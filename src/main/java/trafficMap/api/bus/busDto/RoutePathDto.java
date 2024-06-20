package trafficMap.api.bus.busDto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RoutePathDto {
  private Integer no; //순번
  private double gpsX; //좌표X (WGS84)
  private double gpsY; //좌표Y (WGS84)
  private double posX; //좌표X (GRS80)
  private double posY; //좌표Y (GRS80)

  @Getter
  @Setter
  public static class RoutePathResponse{
    private MsgBody msgBody;
  }

  @Getter
  @Setter
  public static class MsgBody{
    private List<RoutePathDto> itemList;
  }


}
