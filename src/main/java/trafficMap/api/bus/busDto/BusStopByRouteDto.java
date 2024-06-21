package trafficMap.api.bus.busDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BusStopByRouteDto {
  private String busRouteAbrv; //노선 약칭
  private String busRouteId; //노선 ID
  private String busRouteNm; //노선명
  private int seq; //순번
  private String section; //구간 ID
  private String station; //정류소 고유 ID
  private String stationNm; //정류소 이름
  private double gpsX; //X좌표 (WGS84)
  private double gpsY; //Y좌표 (WGS84)
  private String direction; //진행 방향
  private String stationNo; //정류소 번호
  private int routeType; //노선 유형
  private String beginTm; //첫차 시간
  private String lastTm; //막차 시간
  private String trnstnid; //회차지 정류소 ID
  private double posX; //좌표X (GRS80)
  private double posY; //좌표Y (GRS80)
  private String arsId; //정류소 고유 번호
  private char transYn; //회차지 여부

  @Getter
  @Setter
  public static class BusStopByRouteResponse{
    private MsgBody msgBody;
  }

  @Getter
  @Setter
  public static class MsgBody{
    private List<BusStopByRouteDto> itemList;
  }
}
