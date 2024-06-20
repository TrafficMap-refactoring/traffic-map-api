package trafficMap.api.find.tmap.service;

import trafficMap.api.find.tmap.service.dto.Elevator;
import trafficMap.api.find.tmap.service.dto.Tmap;

import java.io.IOException;
import java.util.List;

public interface TmapService {

  List<Tmap> getTmapData(String keyword, double longitude, double latitude);

  List<Elevator> getElevatorData(List<Elevator.ElevatorOrderDto> ele) throws Exception;
}
