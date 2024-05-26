package trafficMap.api.subway;

import org.springframework.stereotype.Service;
import trafficMap.api.subway.subwayDto.SubwayInformDTO;

import java.util.List;


public interface SubwayService {
    List<SubwayInformDTO> searchSubwayByName(String name);

}
