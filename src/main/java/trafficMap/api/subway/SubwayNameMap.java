package trafficMap.api.subway;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import trafficMap.api.subway.subwayDto.SubwayNumDTO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SubwayNameMap {

    private final Map<String, SubwayNumDTO> subwayNameMap;

    public SubwayNameMap(){
        subwayNameMap = initMap();
    }

    public SubwayNumDTO callNumDTObyName(String name){
        return subwayNameMap.get(name);
    }

    @SneakyThrows
    private Map<String, SubwayNumDTO> initMap() {

        File doc = new File(new File("./src/main/resources/SubwayNumber.txt").getCanonicalPath());
        BufferedReader obj = new BufferedReader(new InputStreamReader(new FileInputStream(doc), "utf-8"));
        String[] Name;
        String str;
        String RAIL_OPR_ISTT_CD;
        String LN_CD;
        String STIN_CD;
        String SubwayName;
        String test;

        Map<String, SubwayNumDTO> map = new HashMap<String, SubwayNumDTO>();
        while ((str = obj.readLine()) != null) {
            Name = str.split("\\t");

            SubwayName = Name[3] + " " + Name[5];
            String SubwayName2 = SubwayName.replaceAll("\\(.*?\\)", "");

            SubwayNumDTO subwayNumDto = new SubwayNumDTO();
            subwayNumDto.setRAIL_OPR_ISTT_CD(Name[0]);
            subwayNumDto.setLN_CD(Name[2]);
            subwayNumDto.setSTIN_CD(Name[4]);

            map.put(SubwayName2, subwayNumDto);
        }
        return map;
    }

}
