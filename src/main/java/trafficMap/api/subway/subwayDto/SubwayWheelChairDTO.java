package trafficMap.api.subway.subwayDto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubwayWheelChairDTO {

    private String dtlLoc;
    private String exitNo;
    private String grndDvNmFr;
    private String grnmDvNmTo;
    private String runStinFlorFr;
    private String runStinFlorTo;

    /*
    dtlLoc
exitNo
grndDvNmFr
grndDvNmTo
runStinFlorFr
runStinFlorTo

상세위치
출구번호
운행시작(지상/지하)
운행종료(지상/지하)
운행시작층
운행종료층
     */

}
