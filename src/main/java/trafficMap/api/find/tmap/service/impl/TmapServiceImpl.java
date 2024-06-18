package trafficMap.api.find.tmap.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;
import trafficMap.api.config.ResponseCode;
import trafficMap.api.config.exception.ApiException;
import trafficMap.api.find.tmap.service.Elevator;
import trafficMap.api.find.tmap.service.Tmap;
import trafficMap.api.find.tmap.service.TmapService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class TmapServiceImpl implements TmapService {
  @Value("${tmap.appkey}")
  private String tmapApiKey; // 티맵 API 앱키
  @Value("${elevator.appkey}")
  private String elevatorApiKey; // 엘리베이터 API 앱키

  private final static String TMAP_URL = "https://apis.openapi.sk.com";
  private final static String ELEVATOR_URL = "http://openapi.elevator.go.kr";

  /**
   *  TMAP POI 명칭 검색 + 승강기 운행정보 조회
   * @param keyword   검색어
   * @param longitude 경도
   * @param latitude  위도
   * @return
   */
  @Override
  public List<Tmap> getTmapData(String keyword, double longitude, double latitude) {

    // TMAP POI 명칭 검색 API
    WebClient webClient = WebClient.builder()
        .baseUrl(TMAP_URL)
        .defaultHeader("appKey", tmapApiKey)
        .build();

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    ResponseEntity<String> result = webClient.get()
        .uri(uriBuilder -> uriBuilder.path("/tmap/pois")
        .queryParam("version", 1)        // 버전
        .queryParam("searchKeyword", keyword)   // 검색 키워드
        .queryParam("count", 10)         // 개수
        .queryParam("searchtypCd", "A")  // 정확도순
        .queryParam("radius", 0)         // 반경
        .queryParam("centerLon", longitude)     // 경도
        .queryParam("centerLat", latitude)      // 위도
        .build())
        .retrieve()
        .toEntity(String.class)
        .block();

    if (result != null && result.getBody() != null) {
      try {
        // tmap api 결과 파싱
        Tmap.TmapResponse response = objectMapper.readValue(result.getBody(), Tmap.TmapResponse.class);
        List<Tmap> tmapList = response.getSearchPoiInfo().getPois().getPoi();

        // 각 Tmap 객체에 승강기 운행정보 반환
        List<Elevator.ElevatorOrderDto> elevatorOrderDtoList = new ArrayList<>();

        // Tmap 검색으로 가져온 결과 주소 설정
        IntStream.range(0, tmapList.size())
            .forEach(i -> {
          Tmap tmap = tmapList.get(i);
          Elevator.ElevatorOrderDto elevatorOrderDto = new Elevator.ElevatorOrderDto();

          String address = tmap.getMiddleAddrName() + " " + tmap.getRoadName() + " " + tmap.getFirstBuildNo();
          String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);

          elevatorOrderDto.setAddress(encodedAddress);
          elevatorOrderDto.setOrder(i);
          elevatorOrderDtoList.add(elevatorOrderDto);
        });

        // 설정한 주소로 엘리베이터 운행정보 조회
        List<Elevator> elevatorList = getElevatorData(elevatorOrderDtoList);

        // 운행정보 설정
        IntStream.range(0, tmapList.size())
            .forEach(i -> {
              Tmap tmap = tmapList.get(i);
              Elevator elevator = elevatorList.get(i);
              tmap.setElvtrSttsNm(elevator.getElvtrSttsNm());
            });

        return tmapList;
      } catch (Exception e) {
        throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
      }
    }
    return null;
  }

  /**
   * 승강기 데이터 병렬처리하여 반환
   *  @param ele   승강기 주소,순서가 담긴 리스트
   *  @return
   */
  public List<Elevator> getElevatorData(List<Elevator.ElevatorOrderDto> ele) {
   try {
     ObjectMapper objectMapper = new XmlMapper();
     objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

     // 승강기 api 병렬처리하여 호출
     ParallelFlux<String> responseResults = fetchElevator(ele);

     List<Elevator> elevatorList = responseResults
         .flatMap(res -> {
           try {
             String elvtrSttsNm = extractElvtrSttsNm(res); // 승강기 운행정보
             Elevator elevator = new Elevator();
               elevator.setElvtrSttsNm(elvtrSttsNm);
             return Mono.just(elevator);
           } catch (Exception e) {
             throw new ApiException(ResponseCode.HTTP_INTERFACE_API_ERROR);
           }
         })
         .sequential()
         .collect(Collectors.toList())
         .block();
       return elevatorList != null ? elevatorList : new ArrayList<>();
     } catch (Exception e) {
       throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
     }
  }

  /**
   *  승강기 데이터 xml 파싱 후 운행 정보 추출
   *
   */
  private String extractElvtrSttsNm(String xmlResponse) throws Exception {
    // Xml 파싱
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();

    Document document = builder.parse(new InputSource(new StringReader(xmlResponse)));

    XPathFactory xPathFactory = XPathFactory.newInstance();
    XPath xpath = xPathFactory.newXPath();

    // 운행정보 추출
    XPathExpression expression = xpath.compile("//elvtrSttsNm");

    Node node = (Node) expression.evaluate(document, XPathConstants.NODE);
    return node != null ? node.getTextContent() : null;
  }

  /**
   *  주소로 승강기 운행정보 조회
   */
  public Mono<String> getEle(Elevator.ElevatorOrderDto ele) {

    DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(ELEVATOR_URL);
    factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
    WebClient wc = WebClient.builder().uriBuilderFactory(factory).baseUrl(ELEVATOR_URL).build();

    return wc.get()
        .uri(uriBuilder -> uriBuilder.path("/openapi/service/ElevatorOperationService/getOperationInfoList")
        .queryParam("serviceKey", elevatorApiKey)
        .queryParam("buld_address", ele.getAddress())
        .queryParam("numOfRows",1)
        .queryParam("pageNo",1)
        .build())
        .retrieve()
        .bodyToMono(String.class);
  }

  /**
   * 데이터 병렬 처리
   */
  public ParallelFlux<String> fetchElevator(List<Elevator.ElevatorOrderDto> adds) throws Exception {
    ParallelFlux<Elevator.OrderedResult> res = Flux.fromIterable(adds)
        .parallel() // 병렬 처리
        .runOn(Schedulers.parallel()) // 병렬 스케줄러에서 실행
        .flatMap(dto -> getEle(dto)
            .map(result -> new Elevator.OrderedResult(dto.getOrder(), result))
        );

    return res
        .sequential() // 순차적 처리
        .sort(Comparator.comparingInt(Elevator.OrderedResult::getOrder)) // order 순 정렬
        .map(Elevator.OrderedResult::getResult)
        .parallel(); // ParallelFlux<String>로 변환하기 위함
  }
}