package trafficMap.api.subway;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import trafficMap.api.subway.subwayDto.SubwayWheelChairDTO;

import java.util.List;

@SpringBootTest
public class SubwayWheelchairTest {

    @Autowired
    SubwayService subwayService;

    @Test
    void subwayWheelChairTest(){
        List<SubwayWheelChairDTO> wheelchair = subwayService.subwayWheelchair("3호선 종로3가");

        System.out.println("wheelchair = " + wheelchair);
    }
}
