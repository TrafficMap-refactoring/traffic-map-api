package trafficMap.api.subway;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import trafficMap.api.subway.subwayDto.SubwayInformDTO;
import trafficMap.api.subway.subwayDto.SubwayWheelChairDTO;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubwayServiceImpl implements SubwayService {

    @Value("${subway.url}")
    String subway_url;

    @Value("${subway.api.key}")
    String subway_apikey;

    @Override
    public List<SubwayInformDTO> searchSubwayByName(String name) {

        //RestTemplate : REST API 호출이후 응답을 받을 때까지 기다리는 동기방식
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders(); //헤더
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8)); // 한글깨짐 방지

        String encodedName;
        try {
            encodedName = URLEncoder.encode(name, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        //URI 생성
        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(subway_url)
                .path(subway_apikey + "/json/realtimeStationArrival/0/15/" + encodedName)
                .build(true);

        //response
        ResponseEntity<String> result = restTemplate.exchange(uri.toUri(), HttpMethod.GET, new HttpEntity<String>(headers), String.class);

        //받아온 JSON 데이터 가공
        //json parser
        JSONParser parser = new JSONParser();
        JSONObject object = null;
        try {
            object = (JSONObject) parser.parse(result.getBody());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONArray realtimeArrivalList = (JSONArray) object.get("realtimeArrivalList");

        if (realtimeArrivalList == null) { // 결과가 없으면 null 처리
            return null;
        } else {
            List<SubwayInformDTO> dtos = new ArrayList<>();
            for (int i = 0; i < realtimeArrivalList.size(); i++) { //받아올 데이터 개수만큼 반복

                JSONObject array = (JSONObject) realtimeArrivalList.get(i);
                SubwayInformDTO subwayDto = new SubwayInformDTO();

                String subwayId = (String) array.get("subwayId"); // 지하철호선 ID
                String updnLine = (String) array.get("updnLine"); // 상행/하행 표시
                String trainLineNm = (String) array.get("trainLineNm"); // 도착지 방면
                String statnId = (String) array.get("statnId"); // 지하철역 ID
                String statnNm = (String) array.get("statnNm"); // 지하철역 명
                String btrainSttus = (String) array.get("btrainSttus"); // 열차종류
                String barvlDt = (String) array.get("barvlDt"); // 열차도착예정시간(단위:초)
                String btrainNo = (String) array.get("btrainNo"); // 열차 번호
                String arvlMsg2 = (String) array.get("arvlMsg2"); // 첫번째 도착메세지
                String arvlMsg3 = (String) array.get("arvlMsg3"); // 두번째 도착메세지
                String arvlCd = (String) array.get("arvlCd"); // 도착 코드(0:진입, 1:도착, 2:출발, 3:전역출발, 4:전역진입, 5:전역도착, 99:운행중)

                switch (subwayId) { // subwayId가 알아보기 힘들 것 같아서 알아볼 수 있도록 이름 바꿔서 저장해주기 위한 switch문
                    case "1001":
                        subwayId = "1호선";
                        break;
                    case "1002":
                        subwayId = "2호선";
                        break;
                    case "1003":
                        subwayId = "3호선";
                        break;
                    case "1004":
                        subwayId = "4호선";
                        break;
                    case "1005":
                        subwayId = "5호선";
                        break;
                    case "1006":
                        subwayId = "6호선";
                        break;
                    case "1007":
                        subwayId = "7호선";
                        break;
                    case "1008":
                        subwayId = "8호선";
                        break;
                    case "1009":
                        subwayId = "9호선";
                        break;
                    case "1063":
                        subwayId = "경의중앙선";
                        break;
                    case "1065":
                        subwayId = "공항철도";
                        break;
                    case "1067":
                        subwayId = "경춘선";
                        break;
                    case "1075":
                        subwayId = "수인분당선";
                        break;
                    case "1077":
                        subwayId = "신분당선";
                        break;
                    case "1091":
                        subwayId = "인천공항자기부상철도";
                        break;
                    case "1092":
                        subwayId = "우이신설선";
                        break;
                    default:
                        subwayId = null;
                        break;
                }
                subwayDto.setSubwayId(subwayId);
                subwayDto.setUpdnLine(updnLine);
                subwayDto.setTrainLineNm(trainLineNm);
                subwayDto.setStatnId(statnId);
                subwayDto.setStatnNm(statnNm);
                subwayDto.setBtrainSttus(btrainSttus);
                subwayDto.setBaryId(barvlDt);
                subwayDto.setBtrainNo(btrainNo);
                subwayDto.setArvlMsg2(arvlMsg2);
                subwayDto.setArvlMsg3(arvlMsg3);
                subwayDto.setArvlCd(arvlCd);

                dtos.add(i, subwayDto);
            }

            return dtos;

        }

    }

    @Override
    public List<SubwayWheelChairDTO> subwayWheelchair(String name) {
        return null;
    }
}
