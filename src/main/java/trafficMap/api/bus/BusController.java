package trafficMap.api.bus;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import trafficMap.api.bus.busDto.BusArrInfoByRouteDto;
import trafficMap.api.bus.busDto.BusArrInfoDto;
import trafficMap.api.bus.busDto.BusInfoDto;
import trafficMap.api.bus.busDto.BusStopDto;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/bus")
public class BusController {
  private final BusService busService;


  @GetMapping("/busstop")
  public List<BusStopDto> getBusStop(@RequestParam("keyword") String keyword){  //버스 정류소 검색
    return busService.getBusStopList(keyword);
  }

  @GetMapping("/arrinfo")
  public List<BusArrInfoDto> getBusArrInfo(@RequestParam("arsId") String arsId){ //정류소 버스 도착 예정 정보
    return busService.getBusArrInfoList(arsId);
  }

  @GetMapping("/arrinfobyroute") //노선 버스 위치 정보
  public List<BusArrInfoByRouteDto> getBusArrInfoByRoute(@RequestParam("busRouteId") String busRouteId){
    return busService.getBusArrInfoByRouteList(busRouteId);
  }

  @GetMapping("/businfo")
  public List<BusInfoDto> getBusInfo(@RequestParam("strSrch") String strSrch){
    return busService.getBusInfoList(strSrch);
  }
}
