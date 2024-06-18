package trafficMap.api.find.tmap.service;

import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Map;

public interface TmapService {

  List<Tmap> getTmapData(String keyword, double longitude, double latitude);

  List<Elevator> getElevatorData(List<Elevator.ElevatorOrderDto> ele) throws Exception;

}
