package trafficMap.api.subway;

import org.springframework.stereotype.Service;
import trafficMap.api.subway.subwayDto.*;

import java.util.List;
import java.util.Map;


public interface SubwayService {
    List<SubwayInformDTO> searchSubwayByName(String name);
    List<SubwayWheelChairDTO> subwayWheelchair(String name);
    List<SubwayMoveRouteDTO> subwayMoveRoute(String name);
    List<SubwayElevatorDTO> subwayElevator(String name);
    List<SubwayToiletDTO> subwayToilet(String name);
    SubwayPhotoUrlDTO subwayPhotoUrl(String name);

    List<String> subwayPhotoIncheon(String name);
}
