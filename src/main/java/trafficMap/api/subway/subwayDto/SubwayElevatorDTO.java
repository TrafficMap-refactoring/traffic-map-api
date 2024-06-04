package trafficMap.api.subway.subwayDto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubwayElevatorDTO {

    String dtlLoc; //상세 위치
    String exitNo; // 출구번호
    String grndDvNmFr; //운행시작
    String grndDvNmTo; //운행 종료
    String runStinFlorFr; //운행 시작층
    String runStinFlorTo; //운행 종료층

}
