package trafficMap.api.find.tmap.service;

import trafficMap.api.find.tmap.service.dto.Stair;

import java.io.IOException;
import java.util.List;

public interface StairService {
  List<Stair> getStairs() throws IOException;
}
