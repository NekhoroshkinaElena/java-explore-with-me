package ru.practicum.ewm.event.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.event.mapper.TimeMapper;

import java.time.LocalDateTime;

@Slf4j
@Service
public class EventClient extends BaseClient {

    @Autowired
    public EventClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public void saveRequestInStatistic(String uri, String ip) {
        HitDtoInput hitDtoInput = new HitDtoInput(0L, "explore-with-me",
                uri, ip, TimeMapper.timeToString(LocalDateTime.now()));
        post("/hit", hitDtoInput);
    }

    public int getViewsForEvent(long eventId) {
        String path = "/stats?start=2020-05-05 00:00:00&end=2030-05-05 00:00:00&uris=/events/" + eventId;
        int hits = 0;
        try {
            ViewsForEvent[] stats = rest.getForObject(path, ViewsForEvent[].class);
            if (stats != null && stats.length > 0) {
                hits =  stats[0].getHits();
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return hits;
    }
}
