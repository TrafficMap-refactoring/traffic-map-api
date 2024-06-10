package trafficMap.api.find.tmap.service;

import reactor.core.publisher.Mono;
import java.util.List;

public interface TmapService {

  List<Tmap> getTmapData(String keyword, double longitude, double latitude);
  Mono<String> getElevatorData(String address);

}
