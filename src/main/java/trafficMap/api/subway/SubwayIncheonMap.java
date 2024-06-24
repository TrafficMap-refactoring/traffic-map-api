package trafficMap.api.subway;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SubwayIncheonMap {

    private MultiValueMap<String,String> map;

    public SubwayIncheonMap(){
        this.map=initMap();
    }

    @SneakyThrows
    private MultiValueMap<String,String> initMap(){
        File doc = new File(new File("./src/main/resources/SubwayPhotoFileName.txt").getCanonicalPath());

        BufferedReader obj = new BufferedReader(new InputStreamReader(new FileInputStream(doc), "utf-8"));
        String[] Name;
        String str;
        String key;
        String value;
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        while ((str = obj.readLine()) != null) {
            Name = str.split("\\t");
            key = Name[0];
            value = Name[1];
            map.add(key, value);
        }
        return map;
    }

    public List<String> getIncheonPhoto(String name){
        return map.get(name);
    }
}
