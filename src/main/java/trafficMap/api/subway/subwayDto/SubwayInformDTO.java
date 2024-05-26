package trafficMap.api.subway.subwayDto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubwayInformDTO {
    /*

    호선
    상하행선 구분 (2호선:(내선:0,외선:1, 상행, 하행)
    도착지방면
    지하철역ID
    지하철역명
    열차종류(급행,ITX)
    열차도착예정시간(단위:초)
    열차 번호
    첫번째 도착메세지(전역진입, 전역도착 등)
    두번째 도착메세지(종합운동장 도착, 12분후(광명사거리) 등)
    도착 코드(0:진입, 1:도착, 2:출발, 3:전역출발, 4:전역진입, 5:전역도착, 99:운행중)

    subwayId
    updnLine
    trainLineNm
    statnId
    statnNm
    btrainSttus
    barvID
    brainNo
    arvlMsg2
    arvlMsg3
    arvlCd
     */

    private String subwayId;
    private String updnLine;
    private String trainLineNm;
    private String statnId;
    private String statnNm;
    private String btrainSttus;
    private String baryId;
    private String btrainNo;
    private String arvlMsg2;
    private String arvlMsg3;
    private String arvlCd;

}
