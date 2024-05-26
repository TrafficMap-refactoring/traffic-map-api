package trafficMap.api.subway;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/subway")
public class subwayController {

    @GetMapping
    public String searchSubway(@RequestParam("name") String name){
        return "test";
    }

}
