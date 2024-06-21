package trafficMap.api.find.service.impl;

import org.springframework.stereotype.Service;
import trafficMap.api.config.ResponseCode;
import trafficMap.api.config.exception.ApiException;
import trafficMap.api.find.service.ElevatorService;
import trafficMap.api.find.service.dto.Elevator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class ElevatorServiceImpl implements ElevatorService {

    @Override
    public List<Elevator.ElevatorDto> getElavators() {
        try {
        File doc = new File(new File("./src/main/resources/elevator.txt").getCanonicalPath());

        BufferedReader obj = new BufferedReader(new InputStreamReader(new FileInputStream(doc), "utf-8"));
        String[] Name;
        String str;
        String name;
        String lati;
        String longt;

        int j = 0;

        List<Elevator.ElevatorDto> dtos = new ArrayList<>();

        while ((str = obj.readLine()) != null) {
            Name = str.split("\\t");
            name = Name[0];
            lati = Name[1];
            longt = Name[2];

            Elevator.ElevatorDto elevatorDto = new Elevator.ElevatorDto();


            //일단 테스트로 이제 가공한 데이터를 stairDto에 저장

            elevatorDto.setObjectid(Long.valueOf(j));
            elevatorDto.setLatitude(Double.valueOf(lati));
            elevatorDto.setLongitude(Double.valueOf(longt));

            dtos.add(j, elevatorDto);
            j += 1;


        }

        return dtos;
        } catch (Exception e) {
            throw new ApiException(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
