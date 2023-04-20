package ru.practicum.stats.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.stats.dto.EndpointHitDto;
import ru.practicum.stats.dto.ViewStatsDto;
import ru.practicum.stats.service.EndPointHitService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Slf4j
@RestController
public class StatsController {
    private final EndPointHitService endPointHitService;

    public StatsController(EndPointHitService endPointHitService) {
        this.endPointHitService = endPointHitService;
    }


    @PostMapping("/hit")
    public void addHit(@RequestBody EndpointHitDto endpointHitDto) {
        log.info("Добавляем один просмотр, для URI {}", endpointHitDto.getUri());
        endPointHitService.addHit(endpointHitDto);
    }

    @GetMapping("/stats")
    public Collection<ViewStatsDto> getStatistic(@RequestParam String start, @RequestParam String end,
                                                 @RequestParam(required = false) Collection<String> uris,
                                                 @RequestParam(defaultValue = "false") Boolean unique) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        log.info("Статистика для URI {}, начиная с {}, заканчивая {}", uris, start, end);
        LocalDateTime startTime = LocalDateTime.parse(start, dateTimeFormatter);
        LocalDateTime endTime = LocalDateTime.parse(end, dateTimeFormatter);
        return endPointHitService.getStatistic(startTime, endTime, uris, unique);
    }

}