package trafficMap.api.find.tmap.service.impl;

import lombok.RequiredArgsConstructor;;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import trafficMap.api.find.tmap.service.TmapService;

@Service
@RequiredArgsConstructor
public class TmapServiceImpl implements TmapService {

  private final WebClient tmapWebClient;

  /**
   *  tmap API : POI 명칭 검색
   * @param keyword   검색어
   * @param longitude 경도
   * @param latitude  위도
   * @return
   */
  @Override
  public Mono<String> getTmapData(String keyword, double longitude, double latitude) {
    return tmapWebClient.get()
        .uri(uriBuilder -> uriBuilder.path("/tmap/pois")
        .queryParam("version", 1) //버전
        .queryParam("searchKeyword", keyword)
        .queryParam("longitude", longitude)
        .queryParam("latitude", latitude)
        .build())
        .retrieve()
        .bodyToMono(String.class);
  }
}