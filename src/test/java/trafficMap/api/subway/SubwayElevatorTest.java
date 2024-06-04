package trafficMap.api.subway;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import trafficMap.api.subway.subwayDto.SubwayMoveRouteDTO;
import trafficMap.api.subway.subwayDto.SubwayNumDTO;
import trafficMap.api.subway.subwayDto.SubwayWheelChairDTO;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SubwayElevatorTest {

    @Value("${subway.data.api.key}")
    String api;

    @Value("${subway.elevator.url}")
    String url;

    @Value("${subway.moveElevator.url}")
    String moveUrl;


    @Test
    void callApi(){
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(url);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

        WebClient webClient = WebClient.builder().uriBuilderFactory(factory).baseUrl(url).build();




        //URI 생성
        ResponseEntity<String> result = webClient.get().uri(uriBuilder -> uriBuilder.path("")
                .queryParam("serviceKey", api)
                .queryParam("format", "json")
                .queryParam("railOprIsttCd", "S1") //철도운영기관코드
                .queryParam("lnCd", 3) // 선코드
                .queryParam("stinCd", 322) // 역코드
                .build(true)).retrieve().toEntity(String.class).block();

        //response

        JSONParser parser = new JSONParser();
        JSONObject object = null;
        try {
            object = (JSONObject) parser.parse(result.getBody());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONObject header = (JSONObject) object.get("header");

        System.out.println("header = " + header);

        JSONArray body = (JSONArray) object.get("body");
        System.out.println("body = " + body);
/*

        if (header.get("resultCnt").toString().equals("0")) {// 만약 휠체어리프트가 없는 역이면
            System.out.println("휠체어 리프트가 없습니다.");
            return null;
        }

        else { // 휠체어 리프트가 있는 역이면
            JSONArray body = (JSONArray) object.get("body");
            List<SubwayWheelChairDTO> dtos = new ArrayList<>();
            for(int i=0; i<body.size(); i++) { // 개수만큼 반복
                JSONObject array = (JSONObject) body.get(i);
                SubwayWheelChairDTO wheelchairDto = new SubwayWheelChairDTO();

                String dtlLoc = (String) array.get("dtlLoc"); // 상세위치
                String exitNo = (String) array.get("exitNo"); // 출구번호
                String grndDvNmFr = (String) array.get("grndDvNmFr"); // 운행시작(지상/지하)
                String grndDvNmTo = (String) array.get("grndDvNmTo"); // 운행종료(지상/지하)
                Long runStinFlorFr = (Long) array.get("runStinFlorFr"); // 운행시작층
                Long runStinFlorTo = (Long) array.get("runStinFlorTo"); // 운행종료층

                wheelchairDto.setDtlLoc(dtlLoc);
                wheelchairDto.setExitNo(exitNo);
                wheelchairDto.setGrndDvNmFr(grndDvNmFr);
                wheelchairDto.setGrndDvNmTo(grndDvNmTo);
                wheelchairDto.setRunStinFlorFr(runStinFlorFr);
                wheelchairDto.setRunStinFlorTo(runStinFlorTo);

                dtos.add(i, wheelchairDto);
            }
            return dtos;
        }*/
    }

    @Test
    void callApi2(){
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(moveUrl);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

        WebClient webClient = WebClient.builder().uriBuilderFactory(factory).baseUrl(moveUrl).build();




        //URI 생성
        ResponseEntity<String> result = webClient.get().uri(uriBuilder -> uriBuilder.path("")
                .queryParam("serviceKey", api)
                .queryParam("format", "json")
                .queryParam("railOprIsttCd", "S1") //철도운영기관코드
                .queryParam("lnCd", 3) // 선코드
                .queryParam("stinCd", 322) // 역코드
                .build(true)).retrieve().toEntity(String.class).block();

        //response

        JSONParser parser = new JSONParser();
        JSONObject object = null;
        try {
            object = (JSONObject) parser.parse(result.getBody());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONObject header = (JSONObject) object.get("header");




        if (header.get("resultCnt").toString().equals("0")) {// 만약 휠체어리프트가 없는 역이면
            System.out.println("엘리베이터 이동경로가 없습니다.");

        }

        else { // 휠체어 리프트가 있는 역이면
            JSONArray body = (JSONArray) object.get("body");
            List<SubwayMoveRouteDTO> dtos = new ArrayList<>();
            for(int i=0; i<body.size(); i++) { // 개수만큼 반복
                JSONObject array = (JSONObject) body.get(i);

                System.out.println("array = " + array);

                Long mvPathMgNo= (Long) array.get("mvPathMgNo");
                Long mvTpOrdr = (Long) array.get("mvTpOrdr");
                String mvPathDvNm = (String) array.get("mvPathDvNm");
                String mvContDtl = (String) array.get("mvContDtl");

                if(dtos.size()==mvPathMgNo){
                    SubwayMoveRouteDTO subwayMoveRouteDTO = dtos.get(Math.toIntExact(mvPathMgNo) - 1);
                    //List<SubwayMoveRouteDTO.mvDetail> mvDetails = subwayMoveRouteDTO.getMvDetails();
                    SubwayMoveRouteDTO.mvDetail mvDetail = new SubwayMoveRouteDTO.mvDetail();
                    mvDetail.setMvPathOrdr(mvTpOrdr);
                    mvDetail.setMvContDtl(mvContDtl);
                    subwayMoveRouteDTO.getMvDetails().add(mvDetail);
                }else{
                    SubwayMoveRouteDTO subwayMoveRouteDTO = new SubwayMoveRouteDTO();
                    subwayMoveRouteDTO.setMvPathMgNo(mvPathMgNo);
                    subwayMoveRouteDTO.setMvPathDvNm(mvPathDvNm);
                    List<SubwayMoveRouteDTO.mvDetail> mvDetails = new ArrayList<>();
                    SubwayMoveRouteDTO.mvDetail mvDetail = new SubwayMoveRouteDTO.mvDetail();
                    mvDetail.setMvPathOrdr(mvTpOrdr);
                    mvDetail.setMvContDtl(mvContDtl);
                    mvDetails.add(mvDetail);
                    subwayMoveRouteDTO.setMvDetails(mvDetails);
                    dtos.add(Math.toIntExact(mvPathMgNo) - 1,subwayMoveRouteDTO);
                }


            }
            System.out.println("dtos = " + dtos);
        }



    }

}
