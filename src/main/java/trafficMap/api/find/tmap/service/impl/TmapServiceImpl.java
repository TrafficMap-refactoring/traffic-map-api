package trafficMap.api.find.tmap.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import trafficMap.api.find.tmap.service.Tmap;
import trafficMap.api.find.tmap.service.TmapService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TmapServiceImpl implements TmapService {
  @Value("${tmap.appkey}")
  private String tmapApiKey; //티맵 API 앱키 설정

  private final static String TMAP_URL = "https://apis.openapi.sk.com";


  private final WebClient tmapWebClient;

  /**
   *  tmap API : POI 명칭 검색
   * @param keyword   검색어
   * @param longitude 경도
   * @param latitude  위도
   * @return
   */
  @Override
  public List<Tmap> getTmapData(String keyword, double longitude, double latitude) {

    WebClient webClient = WebClient.builder()
        .baseUrl(TMAP_URL)
        .defaultHeader("appKey", tmapApiKey)
        .build();

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    ResponseEntity<String> result = webClient.get()
        .uri(uriBuilder -> uriBuilder.path("/tmap/pois")
        .queryParam("version", 1) //버전
        .queryParam("searchKeyword", keyword)
        .queryParam("longitude", longitude)
        .queryParam("latitude", latitude)
        .build())
        .retrieve()
        .toEntity(String.class)
        .block();

    if (result != null && result.getBody() != null) {
      try {
        Tmap.TmapResponse response = objectMapper.readValue(result.getBody(), Tmap.TmapResponse.class);
        return response.getSearchPoiInfo().getPois().getPoi();
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @Override
  public Mono<String> getElevatorData(String address) {
    return null;
  }
}