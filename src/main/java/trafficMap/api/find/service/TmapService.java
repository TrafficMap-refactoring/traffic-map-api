package trafficMap.api.find.service;

import trafficMap.api.find.service.dto.Elevator;
import trafficMap.api.find.service.dto.Tmap;

import java.util.List;

public interface TmapService {

  List<Tmap> getTmapData(String keyword, double longitude, double latitude);

  List<Elevator> getElevatorData(List<Elevator.ElevatorOrderDto> ele) throws Exception;

  String getReverseGeocoding(String latitude, String longitude);
}
