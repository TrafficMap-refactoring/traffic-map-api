package trafficMap.api.subway;

import org.springframework.stereotype.Service;
import trafficMap.api.subway.subwayDto.SubwayInformDTO;
import trafficMap.api.subway.subwayDto.SubwayWheelChairDTO;

import java.util.List;


public interface SubwayService {
    List<SubwayInformDTO> searchSubwayByName(String name);
    List<SubwayWheelChairDTO> subwayWheelchair(String name);

}
