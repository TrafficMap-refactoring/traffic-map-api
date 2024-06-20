package trafficMap.api.subway.subwayDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubwayToiletDTO {

    private String dtlLoc; // 상세위치
    private String exitNo; // 출구 번호
    private String gateInotDvNm; // 게이트 내외 구분외
    private Long stinFlor; // 역층
    private Long toltNum; // 화장실 개수
    private String mlFmlDvNm;

}
