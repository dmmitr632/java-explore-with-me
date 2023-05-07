package ru.practicum.stats.statsclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.stats.statsdto.dto.EndpointHitDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Slf4j
@Service
public class StatsClient extends BaseClient {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build()
        );
    }

    public ResponseEntity<Object> addHit(HttpServletRequest request) {
        log.info("StatsClient addHit(), request {}", request);

        EndpointHitDto endPointHitDto =
                EndpointHitDto.builder().ip(request.getRemoteAddr()).app("$(spring.application.name)")
                        .uri(request.getRequestURI()).timestamp(LocalDateTime.now().format(DATE_TIME_FORMATTER)).build();

        return post("/hit", endPointHitDto);
    }

    public ResponseEntity<Object> getStatistic(LocalDateTime start, LocalDateTime end, List<String> uris,
                                               Boolean unique) {

        String line = String.format("/stats?start=%s&end=%s&%sunique=%s}", start.format(DATE_TIME_FORMATTER),
                end.format(DATE_TIME_FORMATTER), "", unique);

        return get(line);
    }
}
