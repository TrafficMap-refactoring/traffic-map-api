package trafficMap.api.bus.busDto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BusArrInfoDto {
  private String adirection; //방향
  private String arrmsg1; //첫번째 도착 예정 버스의 도착 정보 메세지
  private String arrmsg2; //두번째 도착 예정 버스의 도착 정보 메세지
  private String arrmsgSec1; //첫번째 도착 예정 버스의 도착 정보 메세지
  private String arrmsgSec2; //두번째 도착 예정 버스의 도착 정보 메세지
  private String busRouteId; //노선 ID
  private int busType1; //첫번째 도착 예정 버스의 차량 유형
  private int busType2; //두번째 도착 예정 버스의 차량 유형
  private int congestion1; //첫번째 버스 혼잡도
  private int congestion2; //두번째 버스 혼잡도
  private String firstTm; //첫차 시간
  private int isArrive1; //첫번째 도착 예정 버스의 최종 정류소 도착 출발 여부
  private int isArrive2; //두번째 도착 예정 버스의 최종 정류소 도착 출발 여부
  private int isFullFlag1; //첫번째 도착 예정 버스의 만차 여부
  private int isFullFlag2; //두번째 도착 예정 버스의 만차 여부
  private int isLast1; //첫번째 도착 예정 버스의 막차 여부
  private int isLast2; //두번째 도착 예정 버스의 막차 여부
  private String lastTm; //막차 시간
  private String nxtStn; //다음 정류장
  private int routeType; //노선 유형
  private String rtNm; //노선명
  private String stId; //정류소 ID
  private String stNm; //정류소명
  private String stationNm1; //첫번째 도착 예정 버스의 최종 정류소명
  private String stationNm2; //두번째 도착 예정 버스의 최종 정류소명
  private int stationTp; //정류소 타입
  private int term; //배차 간격
  private String vehId1; //첫번째 도착 예정 버스 ID
  private String vehId2; //두번째 도착 예정 버스 ID


  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class BusArrInfoResponse{
    private MsgBody msgBody;
  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class MsgBody{
    private List<BusArrInfoDto> itemList;
  }

}



