package trafficMap.api.find.web;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import trafficMap.api.find.service.ElevatorService;
import trafficMap.api.find.service.dto.Elevator;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/find")
public class ElevatorController {

    private final ElevatorService elevatorService;

    /**
     * 인천 엘리베이터 위치 조회 API
     * @return
     */
    @GetMapping(value="/elevator")
    public List<Elevator.ElevatorDto> getElavators() {
        return elevatorService.getElavators();
    }
}
