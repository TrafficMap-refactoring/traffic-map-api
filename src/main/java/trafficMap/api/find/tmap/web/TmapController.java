package trafficMap.api.find.tmap.web;

import lombok.AllArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;
import trafficMap.api.find.tmap.service.Tmap;
import trafficMap.api.find.tmap.service.TmapService;

import java.io.UnsupportedEncodingException;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/find")
public class TmapController {

  private final TmapService tmapService;

  @ResponseBody
  @PostMapping
  public List<Tmap.tmap> FindByAPI(@RequestParam("keyword") String keyword, @RequestParam("longitude") double longitude, @RequestParam("latitude") double latitude) throws UnsupportedEncodingException, ParseException {
    return tmapService.selectAddress(keyword, longitude, latitude); //티맵 api
  }

}
