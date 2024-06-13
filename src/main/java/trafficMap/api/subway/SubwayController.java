package trafficMap.api.subway;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import trafficMap.api.subway.subwayDto.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/subway")
public class SubwayController {


    private final SubwayService subwayService;

    @ResponseBody
    @GetMapping
    public List<SubwayInformDTO> searchSubway(@RequestParam("name") String name){

        List subwayInformList = subwayService.searchSubwayByName(name);
        return subwayInformList;
    }

    @ResponseBody
    @GetMapping("/wheelchair")
    public List<SubwayWheelChairDTO> subwayWheelChair(@RequestParam("name") String name){

        List wheelchairList = subwayService.subwayWheelchair(name);
        return wheelchairList;
    }

    @ResponseBody
    @GetMapping("/elevator")
    public List<SubwayElevatorDTO> subwayElevator(@RequestParam("name") String name){

        return subwayService.subwayElevator(name);
    }

    @ResponseBody
    @GetMapping("/elevator/move")
    public List<SubwayMoveRouteDTO> subwayMoveRoute(@RequestParam("name") String name){
        return subwayService.subwayMoveRoute(name);
    }

    @ResponseBody
    @GetMapping("/toilet")
    public List<SubwayToiletDTO> subwayToilet(@RequestParam("name") String name){
        return subwayService.subwayToilet(name);
    }


}
