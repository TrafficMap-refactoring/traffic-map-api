package trafficMap.api.bus;

import org.springframework.stereotype.Service;
import trafficMap.api.bus.busDto.BusArrInfoDto;
import trafficMap.api.bus.busDto.BusStopDto;

import java.util.List;

@Service
public interface BusService {
  List<BusStopDto> getBusStopList(String keyword);
  List<BusArrInfoDto> getBusArrInfoList(String arsId);
}