package trafficMap.api.bus;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import trafficMap.api.bus.busDto.BusStopDto;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/bus")
public class BusController {
  private final BusService busService;


  @GetMapping("/busstop")
  public List<BusStopDto> getBusStopList(@RequestParam("keyword") String keyword){
    return busService.getBusStopList(keyword);
  }

}
