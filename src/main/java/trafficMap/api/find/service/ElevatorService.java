package trafficMap.api.find.service;

import trafficMap.api.find.service.dto.Elevator;

import java.util.List;

public interface ElevatorService {
    List<Elevator.ElevatorDto> getElavators();
}
