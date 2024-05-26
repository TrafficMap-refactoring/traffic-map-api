package trafficMap.api.find.tmap.service.impl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import trafficMap.api.find.tmap.service.Tmap;
import trafficMap.api.find.tmap.service.TmapService;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TmapServiceImpl implements TmapService {
  @Value("${tmap.appkey}")
  private String tmap_apiKey; //티맵 API 앱키 설정
  @Value("${tmap.url}")
  private String tmap_url;

  @Override
  public List<Tmap.tmap> selectAddress(String name, double longitude, double latitude) throws UnsupportedEncodingException, ParseException {

    DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(tmap_url);
    factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

    WebClient wc = WebClient.builder().uriBuilderFactory(factory).baseUrl(tmap_url).build();

    String encodedName = URLEncoder.encode(name, "UTF-8");

    ResponseEntity<String> result = wc.get()
        .uri(uriBuilder -> uriBuilder.path("/tmap/pois")
            .queryParam("version", 1) //버전
            .queryParam("searchKeyword", encodedName) // 검색 키워드
            .queryParam("count", 10) // 개수
            .queryParam("appKey", tmap_apiKey) // 서비스키
            .queryParam("searchtypCd", "A") // 거리순, 정확도순 검색(거리순 : R, 정확도순 : A)
            .queryParam("radius", 0) // 반경( 0: 전국반경)
            .queryParam("centerLon", longitude) // 중심 좌표의 경도 좌표
            .queryParam("centerLat", latitude) // 중심 좌표의 위도 좌표
            .build())

        .retrieve() //response 불러옴
        .toEntity(String.class)
        .block();
//        long end2 = System.currentTimeMillis();
//        System.out.println("용의자 시간 : " + (end2 - start2) / 1000.0);

    if (result.getBody() != null) {
      //받아온 JSON 데이터 가공
      //json parser
      JSONParser parser = new JSONParser();
      JSONObject object = (JSONObject) parser.parse(result.getBody());
      //searchPoiInfo의 value들
      JSONObject searchPoiInfo = (JSONObject) object.get("searchPoiInfo");
      //pois의 value들
      JSONObject pois = (JSONObject) searchPoiInfo.get("pois");
      //poi의 value는 배열이라 JSONArray 사용
      JSONArray poiArr = (JSONArray) pois.get("poi");

      List<Tmap.tmap> dtos = new ArrayList<>(); //리스트에 담을 dtos 선언
//      List<ElevatorOrderDto> ele = new ArrayList<>();

      for (int i = 0; i < poiArr.size(); i++) {
//        ElevatorOrderDto elevatorOrderDto = new ElevatorOrderDto();
        Tmap.tmap findDto = new Tmap.tmap();
        object = (JSONObject) poiArr.get(i);
        String middleAddrName = (String) object.get("middleAddrName"); // 도로명주소 ㅇㅇ로
        String roadName = (String) object.get("roadName"); // 도로명주소 ㅇㅇ로
        String firstBuildNo = (String) object.get("firstBuildNo"); //건물번호1
        findDto.setMiddleAddrName(middleAddrName);
        findDto.setRoadName(roadName);
        findDto.setFirstBuildNo(firstBuildNo);

        String addr = middleAddrName + " " + roadName + " " + firstBuildNo;

        String encodedAddr = URLEncoder.encode(addr, "UTF-8");

//        elevatorOrderDto.setAddress(encodedAddr);
//        elevatorOrderDto.setOrder(i);
//
//        ele.add(i, elevatorOrderDto);
      }

      Map<Integer, String> elevatorResult = new HashMap<>();
//            long start1 = System.currentTimeMillis();
//      elevatorResult = findElevatorByAPI(ele);
//            long end1 = System.currentTimeMillis();
//            System.out.println("엘레베이터만 걸리는 시간 : " + (end1 - start1) / 1000.0);

      //다시 poi의 value를 받아온 배열을 개수만큼 담기 (검색했을 때 출력하는 리스트 최대 10개)
      for (int i = 0; i < poiArr.size(); i++) {

        Tmap.tmap findDto = new Tmap.tmap();
        object = (JSONObject) poiArr.get(i);

        //이제 newAddress 안의 경도, 위도, 도로명 주소 쓰기 위해 또 파싱
        JSONObject newAddressList = (JSONObject) object.get("newAddressList");
        JSONArray newAddress = (JSONArray) newAddressList.get("newAddress");

        if (newAddress.size() != 0) {
          JSONObject object1 = (JSONObject) newAddress.get(0);

          //이제 필요한 애들 받아오기
          String fullAddressRoad = (String) object1.get("fullAddressRoad"); //도로명 주소
          String centerLat = (String) object1.get("centerLat"); //위도
          String centerLon = (String) object1.get("centerLon"); //경도
          String name1 = (String) object.get("name"); // 이름
          String bizName = (String) object.get("bizName"); // 업종명
          String upperBizName = (String) object.get("upperBizName"); //업종명 대분류
          String middleAddrName = (String) object.get("middleAddrName"); // 도로명주소 ㅇㅇ로
          String roadName = (String) object.get("roadName"); // 도로명주소 ㅇㅇ로
          String firstBuildNo = (String) object.get("firstBuildNo"); //건물번호1

          findDto.setName(name1);
//                    findDto.setFullAddressRoad(fullAddressRoad);
          findDto.setLatitude(Double.parseDouble(centerLat));
          findDto.setLongitude(Double.parseDouble(centerLon));
          findDto.setBizName(bizName);
          findDto.setUpperBizName(upperBizName);
//                    findDto.setMiddleAddrName(middleAddrName);
//                    findDto.setRoadName(roadName);
//                    findDto.setFirstBuildNo(firstBuildNo);

          switch (name) {
            case "상명대학교 제1공학관":
            case "상명대학교 미래백년관":
            case "상명대학교 종합관":
            case "상명대학교 생활예술관":
              findDto.setFullAddressRoad("서울 종로구 홍지문2길 20");
              findDto.setMiddleAddrName("종로구");
              findDto.setRoadName("홍지문2길");
              findDto.setFirstBuildNo("20");
//              findDto.setElevatorState("운행중");

              dtos.add(i, findDto);

              break;
            case "상명대학교 디자인대학":
              findDto.setFullAddressRoad("충남 천안시 동남구 상명대길 31");
              findDto.setMiddleAddrName("동남구");
              findDto.setRoadName("상명대길");
              findDto.setFirstBuildNo("31");
//              findDto.setElevatorState("운행중");

              dtos.add(i, findDto);
              break;
            default:
              findDto.setFullAddressRoad(fullAddressRoad);
              findDto.setMiddleAddrName(middleAddrName);
              findDto.setRoadName(roadName);
              findDto.setFirstBuildNo(firstBuildNo);

//              findDto.setElevatorState(elevatorResult.get(i));

              dtos.add(i, findDto);
              break;
          }
//                String addr = middleAddrName + " " + roadName + " " + firstBuildNo;
//                    findDto.setElevatorState(elevatorResult.get(i));
//                    dtos.add(i, findDto);
        } else { //건물이 아니라 도로 같은거라서 [] 안에 비어있을 경우
          String name1 = (String) object.get("name"); // 이름
          String upperBizName = (String) object.get("upperBizName"); //업종명 대분류
          String frontLat = (String) object.get("frontLat"); //위도
          String frontLon = (String) object.get("frontLon"); //경도

          findDto.setName(name1);
          findDto.setUpperBizName(upperBizName);
          findDto.setLatitude(Double.parseDouble(frontLat));
          findDto.setLongitude(Double.parseDouble(frontLon));

          dtos.add(i, findDto);
        }
      }
//            long end0 = System.currentTimeMillis();
//            System.out.println("총 시간 : " + (end0 - start0) / 1000.0);
      return dtos;
    } else {
      return null;
    }
  }
}
