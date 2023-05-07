package ru.practicum.stats.statsserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.statsdto.dto.EndpointHitDto;
import ru.practicum.stats.statsdto.dto.ViewStatsDto;
import ru.practicum.stats.statsserver.service.EndPointHitService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
public class StatsController {
    private final EndPointHitService endPointHitService;

    public StatsController(EndPointHitService endPointHitService) {
        this.endPointHitService = endPointHitService;
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/hit")
    public EndpointHitDto addHit(@RequestBody @Valid EndpointHitDto endpointHitDto) {
        if (endpointHitDto.getTimestamp() == null) {
            endpointHitDto.setTimestamp(LocalDateTime.now().toString());
        }
        log.info("                                                                           ");
        log.info("========================================");
        log.info("StatsController addHit, endPointHitDto {}", endpointHitDto);
        log.info("========================================");
        log.info("                                                                           ");
        return endPointHitService.addHit(endpointHitDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stats")
    public List<ViewStatsDto> getStatistic(@RequestParam String start, @RequestParam String end,
                                           @RequestParam(required = false) Collection<String> uris,
                                           @RequestParam(defaultValue = "false") Boolean unique) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        log.info("                                                                           ");
        log.info("========================================");
        log.info("StatsController getStatistic, для URI {}, начиная с {}, заканчивая {}", uris, start, end);
        log.info("========================================");
        log.info("                                                                           ");
        
        LocalDateTime startTime = LocalDateTime.parse(start, dateTimeFormatter);
        LocalDateTime endTime = LocalDateTime.parse(end, dateTimeFormatter);

        List<ViewStatsDto> viewStatsDtos = new ArrayList<>(
                endPointHitService.getStatistic(startTime, endTime, uris, unique));
        log.info("Список {}", viewStatsDtos);
        return viewStatsDtos;

    }

}