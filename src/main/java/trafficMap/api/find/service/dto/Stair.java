package trafficMap.api.find.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Stair {
  private String rdnmadr; // 도로명주소
  private Double startlatitude; // 시작위도
  private Double startlongitude; // 시작경도
}
