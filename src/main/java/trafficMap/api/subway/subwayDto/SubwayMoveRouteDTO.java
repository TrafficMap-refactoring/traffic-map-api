package trafficMap.api.subway.subwayDto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubwayMoveRouteDTO {

    @Getter
    @Setter
    public static class mvDetail{
        Long mvPathOrdr;
        String mvContDtl;
    }

    Long mvPathMgNo;
    String mvPathDvNm;
    List<mvDetail> mvDetails;

}
