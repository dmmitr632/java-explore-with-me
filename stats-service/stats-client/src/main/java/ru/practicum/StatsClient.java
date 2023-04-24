package ru.practicum;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import ru.practicum.model.EndpointHit;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

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

    public void hit() {
        EndpointHit endpointHit = new EndpointHit();
        endpointHit.setApp(application);
        try {
            HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(
                    json.writeValueAsString(endpointHit));
            HttpRequest endpointHitRequest = HttpRequest.newBuilder().uri(URI.create(statsServiceUri + "/hit"))
                    .POST(bodyPublisher).header(
                            HttpHeaders.CONTENT_TYPE, "application/json")
                    .header(HttpHeaders.ACCEPT, "application/json").build();

            HttpResponse<Void> response = httpClient.send(endpointHitRequest, HttpResponse.BodyHandlers.discarding());
            logger.info("Ответ от stats-service: {}", response);

        } catch (Exception e) {
            logger.warn("Не получается записать endpointHit");
        }
    }


}
