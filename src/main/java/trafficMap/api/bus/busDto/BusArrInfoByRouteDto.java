package trafficMap.api.bus.busDto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BusArrInfoByRouteDto {
  public String arrmsg1; //첫번째 도착 예정 버스의 도착 정보 메세지
  public String arrmsg2; //두번째 도착 예정 버스의 도착 정보 메세지
  public String arsId; // 정류소 ID
  public String busRouteAbrv; //노선 약칭
  public String busRouteId; //노선 ID
  public int busType1; //첫번째 도착 예정 버스의 차량 유형
  public int busType2; //두번째 도착 예정 버스의 차량 유형
  public String firstTm; //첫차시간
  public int full1; //첫번째 도착 예정 버스의 만차 여부
  public int full2; //두번째 도착 예정 버스의 만차 여부
  public int isArrive1; //첫번재 도착 예정 버스의 최종 정류소 도착 출발 여부
  public int isArrive2; //두번재 도착 예정 버스의 최종 정류소 도착 출발 여부
  public int isLast1; //첫번째 도착 예정 버스의 막차 여부
  public int isLast2; //두번째 도착 예정 버스의 막차 여부
  public String lastTm; //막차 시간
  public String mkTm; //제공 시각
  public String nstnId1; //첫번재 도착 예정 버스의 다음 정류소 ID
  public String nstnId2; //두번째 도착 예정 버스의 다음 정류소 ID
  public String nstnOrd1; //첫번재 도착 예정 버스의 다음 정류소 순번
  public String nstnOrd2; //두번째 도착 예정 버스의 다음 정류소 순번
  public String plainNo1; //첫번째 도착 예정 차량 번호
  public String plainNo2; //두번째 도착 예정 차량 번호
  public int routeType; //노선 유형
  public String rtNm; //노선명
  public String stId; //정류소 고유 ID
  public String stNm; //정류소명
  public int staOrd; // 요청 정류소 순번
  public int term; //배차간격
  public String vehId1; //첫번째 도착 예정 버스 ID
  public String vehId2; //두번째 도착 예정 버스 ID

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class BusArrInfoByRouteResponse{
    private MsgBody msgBody;
  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class MsgBody{
    private List<BusArrInfoByRouteDto> itemList;
  }


}
