package trafficMap.api.subway;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import trafficMap.api.subway.subwayDto.SubwayInformDTO;

import java.util.List;

@SpringBootTest
public class SubwaySearchTest {

    @Autowired
    SubwayService subwayService;


    @Test
    void subwaySearchTest() {

        List<SubwayInformDTO> result=subwayService.searchSubwayByName("서울");
        System.out.println("result = " + result.get(0).getSubwayId());

    }
}
