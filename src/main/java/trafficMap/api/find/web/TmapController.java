package trafficMap.api.find.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import trafficMap.api.find.service.dto.Tmap;
import trafficMap.api.find.service.TmapService;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/find")
public class TmapController {

  private final TmapService tmapService;

  /**
   * tmap API : POI 명칭 검색
   * @param keyword   검색어
   * @param longitude 경도
   * @param latitude  위도
   * @return
   */
  @GetMapping("/tmap")
  public List<Tmap> getTmapData(@RequestParam("keyword") String keyword,
                                  @RequestParam("longitude") double longitude,
                                  @RequestParam("latitude") double latitude) {
    return tmapService.getTmapData(keyword, longitude, latitude);
  }
  @GetMapping(value="/reverse-geo")
  public String getReverseGeocoding(@RequestParam("latitude")String latitude,
                                    @RequestParam("longitude") String longitude) {
    return tmapService.getReverseGeocoding(latitude, longitude);
  }
}
