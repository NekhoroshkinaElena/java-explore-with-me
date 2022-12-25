package ru.practicum.ewm.event.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.event.mapper.TimeMapper;

import java.time.LocalDateTime;

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
}
