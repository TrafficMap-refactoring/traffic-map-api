package trafficMap.api.subway;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import trafficMap.api.config.ResponseCode;
import trafficMap.api.config.exception.ApiException;
import trafficMap.api.subway.subwayDto.*;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@Service
public class SubwayServiceImpl implements SubwayService {

    @Value("${subway.url}")
    String subway_url;

    @Value("${subway.api.key}")
    String subway_apikey;

    @Value("${subway.wheelchair.url}")
    String wheelchair_url;

    @Value("${subway.data.api.key}")
    String subway_data_apikey;

    @Value("${subway.moveElevator.url}")
    String moveUrl;

    @Value("${subway.elevator.url}")
    String elevatorUrl;

    @Value("${subway.toilet.url}")
    String toiletUrl;


    private final SubwayNameMap subwayNameMap;
    private final SubwayPhotoMap subwayPhotoMap;
    private final SubwayIncheonMap subwayIncheonMap;

    @Autowired
    public SubwayServiceImpl(SubwayNameMap subwayNameMap, SubwayPhotoMap subwayPhotoMap, SubwayIncheonMap subwayIncheonMap) {
        this.subwayNameMap = subwayNameMap;
        this.subwayPhotoMap = subwayPhotoMap;
        this.subwayIncheonMap = subwayIncheonMap;
    }

    @Override
    public List<SubwayInformDTO> searchSubwayByName(String name) {

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(subway_url);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

        WebClient webClient = WebClient.builder().uriBuilderFactory(factory).baseUrl(subway_url).build();

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

        ResponseEntity<String> result = webClient.get().uri(uri.toUri()).retrieve().toEntity(String.class).block();


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

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(wheelchair_url);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

        WebClient webClient = WebClient.builder().uriBuilderFactory(factory).baseUrl(wheelchair_url).build();

        SubwayNumDTO subwayNumDto = new SubwayNumDTO();
        try {
            subwayNumDto = subwayNameMap.callNumDTObyName(name);
        } catch (Exception e) {
            throw new ApiException(ResponseCode.METHOD_ARGUMENT_NOT_VALID);
        }

        String lnCd = subwayNumDto.getLN_CD();
        String stinCd = subwayNumDto.getSTIN_CD();
        String railOprIsttCd = subwayNumDto.getRAIL_OPR_ISTT_CD();


        //URI 생성
        ResponseEntity<String> result = webClient.get().uri(uriBuilder -> uriBuilder.path("")
                .queryParam("serviceKey", subway_data_apikey)
                .queryParam("format", "json")
                .queryParam("railOprIsttCd", railOprIsttCd) //철도운영기관코드
                .queryParam("lnCd", lnCd) // 선코드
                .queryParam("stinCd", stinCd) // 역코드
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
            return null;
        } else { // 휠체어 리프트가 있는 역이면
            JSONArray body = (JSONArray) object.get("body");
            List<SubwayWheelChairDTO> dtos = new ArrayList<>();
            for (int i = 0; i < body.size(); i++) { // 개수만큼 반복
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
        }
    }

    @Override
    public List<SubwayElevatorDTO> subwayElevator(String name) {

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(elevatorUrl);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

        WebClient webClient = WebClient.builder().uriBuilderFactory(factory).baseUrl(elevatorUrl).build();


        SubwayNumDTO subwayNumDto = new SubwayNumDTO();
        try {
            subwayNumDto = subwayNameMap.callNumDTObyName(name);
        } catch (Exception e) {
            throw new ApiException(ResponseCode.METHOD_ARGUMENT_NOT_VALID);
        }

        String lnCd = subwayNumDto.getLN_CD();
        String stinCd = subwayNumDto.getSTIN_CD();
        String railOprIsttCd = subwayNumDto.getRAIL_OPR_ISTT_CD();


        //URI 생성
        ResponseEntity<String> result = webClient.get().uri(uriBuilder -> uriBuilder.path("")
                .queryParam("serviceKey", subway_data_apikey)
                .queryParam("format", "json")
                .queryParam("railOprIsttCd", railOprIsttCd) //철도운영기관코드
                .queryParam("lnCd", lnCd) // 선코드
                .queryParam("stinCd", stinCd) // 역코드
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
            return null;
        }
        else { // 휠체어 리프트가 있는 역이면
            JSONArray body = (JSONArray) object.get("body");
            List<SubwayElevatorDTO> dtos = new ArrayList<>();
            for(int i=0; i<body.size(); i++) { // 개수만큼 반복
                JSONObject array = (JSONObject) body.get(i);

                SubwayElevatorDTO subwayElevatorDTO = new SubwayElevatorDTO();

                String dtlLoc = (String) array.get("dtlLoc"); // 상세위치
                String exitNo = (String) array.get("exitNo"); // 출구번호
                String grndDvNmFr = (String) array.get("grndDvNmFr"); // 운행시작(지상/지하)
                String grndDvNmTo = (String) array.get("grndDvNmTo"); // 운행종료(지상/지하)
                Long runStinFlorFr = (Long) array.get("runStinFlorFr"); // 운행시작층
                Long runStinFlorTo = (Long) array.get("runStinFlorTo"); // 운행종료층

                subwayElevatorDTO.setDtlLoc(dtlLoc);
                subwayElevatorDTO.setExitNo(exitNo);
                subwayElevatorDTO.setGrndDvNmFr(grndDvNmFr);
                subwayElevatorDTO.setGrndDvNmTo(grndDvNmTo);
                subwayElevatorDTO.setRunStinFlorFr(runStinFlorFr);
                subwayElevatorDTO.setRunStinFlorTo(runStinFlorTo);

                dtos.add(i, subwayElevatorDTO);
            }
            return dtos;
        }
    }

    @Override
    public List<SubwayMoveRouteDTO> subwayMoveRoute(String name) {
        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(moveUrl);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

        WebClient webClient = WebClient.builder().uriBuilderFactory(factory).baseUrl(moveUrl).build();


        SubwayNumDTO subwayNumDto = new SubwayNumDTO();
        try {
            subwayNumDto = subwayNameMap.callNumDTObyName(name);
        } catch (Exception e) {
            throw new ApiException(ResponseCode.METHOD_ARGUMENT_NOT_VALID);
        }

        String lnCd = subwayNumDto.getLN_CD();
        String stinCd = subwayNumDto.getSTIN_CD();
        String railOprIsttCd = subwayNumDto.getRAIL_OPR_ISTT_CD();
        //URI 생성
        ResponseEntity<String> result = webClient.get().uri(uriBuilder -> uriBuilder.path("")
                .queryParam("serviceKey", subway_data_apikey)
                .queryParam("format", "json")
                .queryParam("railOprIsttCd", railOprIsttCd) //철도운영기관코드
                .queryParam("lnCd", lnCd) // 선코드
                .queryParam("stinCd", stinCd) // 역코드
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
            return null;

        } else { // 휠체어 리프트가 있는 역이면
            JSONArray body = (JSONArray) object.get("body");

            Map<String,SubwayMoveRouteDTO> dtoMap = new HashMap<>();
            int last = 0;
            for (int i = 0; i < body.size(); i++) { // 개수만큼 반복
                JSONObject array = (JSONObject) body.get(i);



                Long mvPathMgNo = (Long) array.get("mvPathMgNo");
                Long mvTpOrdr = (Long) array.get("mvTpOrdr");
                String mvPathDvNm = (String) array.get("mvPathDvNm");
                String mvContDtl = (String) array.get("mvContDtl");


                String s = mvPathMgNo+mvPathDvNm;
                SubwayMoveRouteDTO subwayMoveRouteDTO = dtoMap.getOrDefault(s, new SubwayMoveRouteDTO());


                List<SubwayMoveRouteDTO.mvDetail> mvDetails = new ArrayList<>();
                SubwayMoveRouteDTO.mvDetail mvDetail = new SubwayMoveRouteDTO.mvDetail();
                mvDetail.setMvPathOrdr(mvTpOrdr);
                mvDetail.setMvContDtl(mvContDtl);
                if(subwayMoveRouteDTO.getMvDetails() == null){
                    subwayMoveRouteDTO.setMvPathMgNo(mvPathMgNo);
                    subwayMoveRouteDTO.setMvPathDvNm(mvPathDvNm);
                    mvDetails.add(mvDetail);
                    subwayMoveRouteDTO.setMvDetails(mvDetails);}
                else{
                    subwayMoveRouteDTO.getMvDetails().add(mvDetail);}


                dtoMap.put(s,subwayMoveRouteDTO);


            }

            Collection<SubwayMoveRouteDTO> values = dtoMap.values();
            List<SubwayMoveRouteDTO> dtos = new ArrayList<>(values);


            return dtos;
        }
    }

    @Override
    public List<SubwayToiletDTO> subwayToilet(String name) {

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(toiletUrl);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);

        WebClient webClient = WebClient.builder().uriBuilderFactory(factory).baseUrl(elevatorUrl).build();


        SubwayNumDTO subwayNumDto = new SubwayNumDTO();
        try {
            subwayNumDto = subwayNameMap.callNumDTObyName(name);
        } catch (Exception e) {
            throw new ApiException(ResponseCode.METHOD_ARGUMENT_NOT_VALID);
        }

        String lnCd = subwayNumDto.getLN_CD();
        String stinCd = subwayNumDto.getSTIN_CD();
        String railOprIsttCd = subwayNumDto.getRAIL_OPR_ISTT_CD();


        //URI 생성
        ResponseEntity<String> result = webClient.get().uri(uriBuilder -> uriBuilder.path("")
                .queryParam("serviceKey", subway_data_apikey)
                .queryParam("format", "json")
                .queryParam("railOprIsttCd", railOprIsttCd) //철도운영기관코드
                .queryParam("lnCd", lnCd) // 선코드
                .queryParam("stinCd", stinCd) // 역코드
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
            return null;
        }
        else { // 휠체어 리프트가 있는 역이면
            JSONArray body = (JSONArray) object.get("body");
            List<SubwayToiletDTO> dtos = new ArrayList<>();
            for (int i = 0; i < body.size(); i++) { // 개수만큼 반복
                JSONObject array = (JSONObject) body.get(i);

                SubwayToiletDTO subwayToiletDTO = new SubwayToiletDTO();

                String dtlLoc = (String) array.get("dtlLoc"); // 상세위치
                String exitNo = (String) array.get("exitNo"); // 출구번호
                String gateInotDvNm= (String) array.get("gateInotDvNm"); // 게이트 내외
                Long stinFlor = (Long) array.get("stinFlor"); // 역층
                Long toltNum = (Long) array.get("toltNum"); // 화장실 개수
                String mlFmlDvNm = (String) array.get("mlFmlDvNm");

                subwayToiletDTO.setDtlLoc(dtlLoc);
                subwayToiletDTO.setExitNo(exitNo);
                subwayToiletDTO.setGateInotDvNm(gateInotDvNm);
                subwayToiletDTO.setStinFlor(stinFlor);
                subwayToiletDTO.setToltNum(toltNum);
                subwayToiletDTO.setMlFmlDvNm(mlFmlDvNm);

                dtos.add(i, subwayToiletDTO);
            }
            return dtos;
        }
    }

    @Override
    public SubwayPhotoUrlDTO subwayPhotoUrl(String name) {

        SubwayPhotoUrlDTO subwayPhotoUrlDTO = new SubwayPhotoUrlDTO();
        subwayPhotoUrlDTO.setUrl(subwayPhotoMap.getStationPhotoUrl(name));
        return subwayPhotoUrlDTO;
    }

    @Override
    public List<String> subwayPhotoIncheon(String name) {
        return subwayIncheonMap.getIncheonPhoto(name);
    }
}