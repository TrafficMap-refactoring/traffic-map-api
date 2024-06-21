package trafficMap.api.find.service;

import trafficMap.api.find.service.dto.Stair;

import java.io.IOException;
import java.util.List;

public interface StairService {
  List<Stair> getStairs() throws IOException;
}
