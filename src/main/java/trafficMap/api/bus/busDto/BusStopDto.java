package trafficMap.api.bus.busDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BusStopDto {
  private String stId; // 정류소 고유 ID
  private String stNm; // 정류소명
  private String tmX; // 정류소 좌표 X (WGS84)
  private String tmY; // 정류소 좌표 Y (WGS84)
  private String arsId; // 정류소 번호
  private String posX; // 정류소 좌표 X (GRS80)
  private String posY; // 정류소 좌표 Y (GRS80)



  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class BusStopResponse {
    private MsgBody msgBody;
  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class MsgBody {
    private List<BusStopDto> itemList;
  }

}
