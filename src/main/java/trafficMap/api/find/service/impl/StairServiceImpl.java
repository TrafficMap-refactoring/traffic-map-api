package trafficMap.api.find.service.impl;

import org.springframework.stereotype.Service;
import trafficMap.api.find.service.StairService;
import trafficMap.api.find.service.dto.Stair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StairServiceImpl implements StairService {

  /**
   * 인천광역시 계단 위치 조회
   */
  @Override
  public List<Stair> getStairs() throws IOException {

    File doc = new File(new File("./src/main/resources/stair.txt").getCanonicalPath());

    BufferedReader obj = new BufferedReader(new InputStreamReader(new FileInputStream(doc), "utf-8"));
    String[] Name;
    String str;
    String name;
    String lati;
    String longt;

    int j = 0;

    List<Stair> dtos = new ArrayList<>();

    while ((str = obj.readLine()) != null) {
      Name = str.split("\\t");
      name = Name[0];
      lati = Name[1];
      longt = Name[2];

      Stair stairDto = new Stair();


      //일단 테스트로 이제 가공한 데이터를 stairDto에 저장

      stairDto.setRdnmadr(name);
      stairDto.setStartlatitude(Double.valueOf(lati));
      stairDto.setStartlongitude(Double.valueOf(longt));


      Pattern str_a = Pattern.compile("아파트");
      if (name == null) {
        dtos.add(j, stairDto);
        j += 1;
      } else {
        Matcher matcher = str_a.matcher(name);
        if (!matcher.find()) {
          dtos.add(j, stairDto);
          j += 1;
        }
      }
    }
    return dtos;
  }
}
