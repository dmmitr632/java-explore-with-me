package ru.practicum.ewm.controller.pub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.dto.EventFullDto;
import ru.practicum.ewm.dto.EventShortDto;
import ru.practicum.ewm.service.EventService;
import ru.practicum.stats.statsclient.StatsClient;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.DateTimeFormatterConstant.DATE_TIME_FORMATTER;

@RestController
@Slf4j
public class EventPublicController {
    private final EventService eventService;
    private final StatsClient statsClient;

    public EventPublicController(EventService eventService, StatsClient statsClient) {
        this.eventService = eventService;
        this.statsClient = statsClient;
    }

    @GetMapping("/events")

    public List<EventShortDto> getEventsPublic(@RequestParam(defaultValue = "", required = false) String text,
                                               @RequestParam(defaultValue = "", required = false) List<Integer> categories,
                                               @RequestParam(defaultValue = "false", required = false) Boolean paid,
                                               @RequestParam(required = false) @DateTimeFormat(pattern =
                                                       DATE_TIME_FORMATTER) LocalDateTime rangeStart,
                                               @RequestParam(required = false) @DateTimeFormat(pattern =
                                                       DATE_TIME_FORMATTER) LocalDateTime rangeEnd,
                                               @RequestParam(defaultValue = "false", required = false) Boolean onlyAvailable,
                                               @RequestParam(defaultValue = "EVENT_DATE", required = false) String sort,
                                               @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                               @Positive @RequestParam(defaultValue = "10") Integer size,
                                               HttpServletRequest httpServletRequest) {
        String ip = httpServletRequest.getRemoteAddr();
        String uri = httpServletRequest.getRequestURI();
        log.info("                                                                           ");
        log.info("========================================");
        log.info("Получение списка событий, text {}, categories {}, paid {}, rangeStart {}, rangeEnd {}, " +
                        "onlyAvailable " +
                        "{}, sort {}, from {}, size {}, ip {}, uri {}", text, categories, paid, rangeStart,
                rangeEnd,
                onlyAvailable,
                sort, from, size, ip, uri);
        log.info("========================================");
        log.info("                                                                           ");
        statsClient.addHit(httpServletRequest);
        return eventService.getEventsPublic(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                sort, from, size, ip, uri);
    }

    @GetMapping("/events/{id}")

    public EventFullDto getEventPublic(@PathVariable Integer id, HttpServletRequest httpServletRequest) {
        String ip = httpServletRequest.getRemoteAddr();
        String uri = httpServletRequest.getRequestURI();
        log.info("                                                                           ");
        log.info("========================================");
        log.info("Получение события по event id {}, ip {}, uri {} ", id, ip, uri);
        log.info("========================================");
        log.info("                                                                           ");
        statsClient.addHit(httpServletRequest);
        return eventService.getEventPublic(id, ip, uri);
    }
}
