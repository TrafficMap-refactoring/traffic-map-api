package trafficMap.api.find.tmap.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Elevator {
  private String elvtrSttsNm; // 운행 상태

  @Getter
  @Setter
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Body {
    private Items items;
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
  public static class ElevatorOrderDto {
    private String address;
    private Integer order;
    private String elvtrSttsNm;

  }

  @Getter
  @Setter
  @AllArgsConstructor
  public static class OrderedResult {
    private int order;
    private String result;

  }
}