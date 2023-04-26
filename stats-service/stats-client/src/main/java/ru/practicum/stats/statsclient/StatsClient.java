package ru.practicum.stats.statsclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.stats.statsdto.dto.EndpointHitDto;
import ru.practicum.stats.statsdto.dto.ViewStatsDto;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Service
@Slf4j
public class StatsClient {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final String application;

    private final String statsServiceUri;
    private final ObjectMapper json;
    private final HttpClient httpClient;


    public StatsClient(@Value("${spring.application.name}") String application,
                       @Value("${spring.stats-service.uri:http://localhost:9090}") String statsServiceUri,
                       ObjectMapper json) {
        this.application = application;
        this.statsServiceUri = statsServiceUri;
        this.json = json;
        this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
    }

    public void addHit() {
        EndpointHitDto endpointHitDto = new EndpointHitDto();
        endpointHitDto.setApp(application);
        try {
            HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(
                    json.writeValueAsString(endpointHitDto));
            HttpRequest endpointHitDtoRequest = HttpRequest.newBuilder().uri(URI.create(statsServiceUri + "/hit"))
                    .POST(bodyPublisher).header(
                            HttpHeaders.CONTENT_TYPE, "application/json")
                    .header(HttpHeaders.ACCEPT, "application/json").build();

            HttpResponse<Void> response = httpClient.send(endpointHitDtoRequest,
                    HttpResponse.BodyHandlers.discarding());
            logger.info("Ответ от stats-service: {}", response);

        } catch (Exception e) {
            logger.warn("Не получается записать EndpointHitDto");
        }
    }

    public ResponseEntity<Collection<ViewStatsDto>> getStatistic(LocalDateTime start, LocalDateTime end,
                                                                 Collection<String> uris,
                                                                 boolean unique) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        RestTemplate restTemplate = new RestTemplate();
        StringBuilder stringBuilder = new StringBuilder();
        uris.forEach(uri -> stringBuilder.append("uris=").append(uri).append("&"));
        String line = String.format("/stats?start=%s&end=%s&%sunique=%s}", start.format(dateTimeFormatter),
                end.format(dateTimeFormatter), stringBuilder, unique);
        return restTemplate.exchange(line, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
    }


}
