package trafficMap.api.bus;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import trafficMap.api.bus.busDto.*;

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

  @GetMapping("/businfo") //노선 번호로 노선 정보 조회
  public List<BusInfoDto> getBusInfo(@RequestParam("strSrch") String strSrch){
    return busService.getBusInfoList(strSrch);
  }

  @GetMapping("/routepath") //지도 상 노선 경로 좌표
  public List<RoutePathDto> getRoutePath(@RequestParam("busRouteId") String busRouteId){
    return busService.getRoutePath(busRouteId);
  }

  @GetMapping("/busstopbyroute")
  public List<BusStopByRouteDto> getBusStopByRoute(@RequestParam("busRouteId") String busRouteId){
    return busService.getBusStopByRoute(busRouteId);
  }

}
