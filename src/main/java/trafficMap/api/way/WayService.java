package trafficMap.api.way;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import trafficMap.api.way.wayDto.WayDTO;

import java.util.List;

@Service
public interface WayService {

    List<WayDTO> findWay(double startX, double startY, double endX, double endY, String startName, String endName, Number option);

    String findTransWay(String sName, String eName);
    JSONObject findDrawWay(double startX, double startY, double endX, double endY, String startName, String endName, Number option);

}
