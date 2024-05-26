package trafficMap.api.find.tmap.service;

import lombok.Getter;
import lombok.Setter;

public class Tmap {

  @Getter
  @Setter
  public static class tmap {
    private String name; //건물 이름(시설물 명칭)
    private double Latitude; //위도
    private double Longitude; //경도
    private String fullAddressRoad; // 도로명 주소(이걸 엘리베이터 찾을 때 써야할듯)
    private String middleAddrName; // ㅇㅇ구
    private String roadName; //도로명 주소 (ex 부평문화로)
    private String firstBuildNo;  //건물번호? (ex 35)
    private String bizName; // 업종명
    private String upperBizName; //업종명 대분류(bizName이 "" 일 수도 있어서)
  }

}
