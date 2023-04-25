package ru.practicum.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.service.EndPointHitService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@ResponseStatus(HttpStatus.OK)
@RestController
public class StatsController {
    private final EndPointHitService endPointHitService;

    public StatsController(EndPointHitService endPointHitService) {
        this.endPointHitService = endPointHitService;
    }


    @PostMapping("/hit")
    public EndpointHitDto addHit(@RequestBody @Valid EndpointHitDto endpointHitDto) {
        if (endpointHitDto.getTimestamp() == null) {
            endpointHitDto.setTimestamp(LocalDateTime.now().toString());
        }
        log.info("Добавляем один просмотр, для URI {}", endpointHitDto.getUri());
        return endPointHitService.addHit(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> getStatistic(@RequestParam String start, @RequestParam String end,
                                           @RequestParam(required = false) Collection<String> uris,
                                           @RequestParam(defaultValue = "false") Boolean unique) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        log.info("Статистика для URI {}, начиная с {}, заканчивая {}", uris, start, end);
        LocalDateTime startTime = LocalDateTime.parse(start, dateTimeFormatter);
        LocalDateTime endTime = LocalDateTime.parse(end, dateTimeFormatter);

        List<ViewStatsDto> viewStatsDtos = new ArrayList<>(
                endPointHitService.getStatistic(startTime, endTime, uris, unique));
        log.info("Список {}", viewStatsDtos);
        return viewStatsDtos;

    }

}