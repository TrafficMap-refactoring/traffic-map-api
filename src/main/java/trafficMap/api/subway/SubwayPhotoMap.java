package trafficMap.api.subway;

import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class SubwayPhotoMap {

    private Map<String, String> stationUrlMap;

    public SubwayPhotoMap() {
        stationUrlMap = new HashMap<>();
        stationUrlMap = loadStationData();
    }

    @SneakyThrows
    private Map<String,String> loadStationData() {
        try (InputStream is = new FileInputStream("./src/main/resources/서울교통공사_비상대피 안내도 정보.json")) {
            JSONTokener tokener = new JSONTokener(is);
            JSONObject object = new JSONObject(tokener);
            JSONArray stationData = object.getJSONArray("DATA");

            for (int i = 0; i < stationData.length(); i++) {
                JSONObject station = stationData.getJSONObject(i);
                String stnNm = station.getString("stn_nm");
                String stnImgUrl = station.getString("stn_img_url");
                stationUrlMap.put(stnNm, stnImgUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stationUrlMap;
    }

    public String getStationPhotoUrl(String stnNm) {
        return stationUrlMap.getOrDefault(stnNm, "Station not found");
    }

}
