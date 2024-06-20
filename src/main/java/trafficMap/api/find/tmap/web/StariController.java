package trafficMap.api.find.tmap.web;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trafficMap.api.find.tmap.service.StairService;
import trafficMap.api.find.tmap.service.dto.Stair;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/find")
public class StariController {

  private final StairService stairService;

  /**
   * 인천광역시 계단 위치 조회
   */
  @GetMapping("/stair")
  public List<Stair> getStairs() throws Exception { //계단 api
    return stairService.getStairs();
  }
}
