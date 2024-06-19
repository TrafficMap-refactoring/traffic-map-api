package trafficMap.api.find.tmap.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tmap {

    private String name; // 건물명(시설물 명칭)
    private double frontLat; // 위도
    private double frontLon; // 경도
    private String fullAddressRoad; // 도로명 주소
    private String middleAddrName; // 구
    private String roadName; // 도로명
    private String firstBuildNo;  // 건물번호
    private String bizName; // 업종명
    private String upperBizName; // 업종명 대분류
    private String elvtrSttsNm;

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class TmapResponse {
    private SearchPoiInfo searchPoiInfo;
  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class SearchPoiInfo {
    private Pois pois;
  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Pois {
    private List<Tmap> poi;
  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Body {
    private Elevator.Items items;
  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Items {
    private List<Elevator> items;
  }

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ElevatorDto {
    private String address;
    private Integer order;
  }
}