package trafficMap.api.way;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import trafficMap.api.way.wayDto.WayDTO;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/api")
public class WayController {

    @Autowired
    WayService wayService;

    @GetMapping("/way")
    @ResponseBody
    public List<WayDTO> FindWay(double startX, double startY, double endX, double endY, String startName, String endName, Number option) {
        return wayService.findWay(startX, startY, endX, endY, startName, endName, option);
    }

    @GetMapping("/way/trans")
    @ResponseBody
    public String FindTransWay4(String sName,String eName){ // 카카오 대중교통 길찾기 연결 -> 출발지, 도착지 이름 or 주소 입력하는 방법
        return wayService.findTransWay(sName, eName);
    }
}
