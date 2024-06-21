package trafficMap.api.bus.busDto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BusInfoDto {
  private String busRouteAbrv; //노선 약칭
  private String busRouteId; //노선 ID
  private String busRoutedNm; //노선명
  private int length; //노선 길이
  private int routeType; //노선 유형
  private String stStationNm; //기점
  private String edStationNm; //종점
  private int term; //배차 간격(분)
  private String lastBustYn; //막차 운행 여부
  private String firstBusTm; //금일 첫차 시간
  private String lastBusTm; //금일 막차 시간
  private String firstLowTm; //금일 저상 첫차 시간
  private String lastLowTm; //금일 저상 막차 시간
  private String corpNm; //운수사명


  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class BusInfoResponse{
    private MsgBody msgBody;
  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class MsgBody{
    private List<BusInfoDto> itemList;
  }

}
