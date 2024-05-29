package trafficMap.api.find.tmap.service;

import org.json.simple.parser.ParseException;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface TmapService {

  Mono<String> getTmapData(String keyword, double longitude, double latitude);
}
