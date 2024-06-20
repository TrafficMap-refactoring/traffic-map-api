package trafficMap.api.bus;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import trafficMap.api.bus.busDto.BusArrInfoDto;
import trafficMap.api.bus.busDto.BusStopDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements BusService{
  @Value("${busstop.appkey}")
  private String busApiKey;
  private final static String BusStop_URL = "http://ws.bus.go.kr/api/rest/stationinfo/getStationByName";

  public List<BusStopDto> getBusStopList(String keyword){

    WebClient webClient = WebClient.builder()
            .baseUrl(BusStop_URL)
            .defaultHeader("serviceKey", busApiKey)
            .build();


    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    ResponseEntity<String> result = webClient.get()
            .uri(uriBuilder -> uriBuilder.path("")
                    .queryParam("stSrch", keyword)
                    .queryParam("resultType", "json")
                    .build())
            .retrieve()
            .toEntity(String.class)
            .block();

    if(result != null && result.getBody() != null){
      try{
        BusStopDto.BusStopResponse res = objectMapper.readValue(result.getBody(), BusStopDto.BusStopResponse.class);
        return res.getMsgBody().getItemList();
      } catch (JsonProcessingException e){
        e.printStackTrace();
      }
    }
    return null;
  }

  private final static String BusArrInfo_URL = "http://ws.bus.go.kr/api/rest/stationinfo/getStationByUid";

  public List<BusArrInfoDto> getBusArrInfoList(String arsId){
    WebClient webClient = WebClient.builder()
            .baseUrl(BusArrInfo_URL)
            .defaultHeader("serviceKey", busApiKey)
            .build();

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    ResponseEntity<String> result = webClient.get()
            .uri(uriBuilder -> uriBuilder.path("")
                    .queryParam("arsId", arsId)
                    .queryParam("resultType", "json")
                    .build())
            .retrieve()
            .toEntity(String.class)
            .block();

    if(result != null && result.getBody() != null){
      try{
        BusArrInfoDto.BusArrInfoResponse res = objectMapper.readValue(result.getBody(), BusArrInfoDto.BusArrInfoResponse.class);
        return res.getMsgBody().getItemList();
      }catch(JsonProcessingException e){
        e.printStackTrace();
      }
    }

    return null;
  }

}
