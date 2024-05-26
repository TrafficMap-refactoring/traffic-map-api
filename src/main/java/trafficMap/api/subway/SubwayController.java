package trafficMap.api.subway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import trafficMap.api.subway.subwayDto.SubwayInformDTO;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/subway")
public class SubwayController {

    @Autowired
    SubwayService subwayService;

    @ResponseBody
    @GetMapping
    public List<SubwayInformDTO> searchSubway(@RequestParam("name") String name){

        List subwayInformList = subwayService.searchSubwayByName(name);
        return subwayInformList;
    }

}
