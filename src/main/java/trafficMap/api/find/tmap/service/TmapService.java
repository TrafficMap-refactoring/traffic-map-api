package trafficMap.api.find.tmap.service;

import org.json.simple.parser.ParseException;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface TmapService {
  List<Tmap.tmap> selectAddress(String name, double longitude, double latitude) throws UnsupportedEncodingException, ParseException; // 티맵 API로 명칭 검색
}
