package ru.practicum.ewm.controller.pub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.EventFullDto;
import ru.practicum.ewm.service.EventService;

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

    public EventPublicController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(path = "/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getEvents(@RequestParam(defaultValue = "", required = false) String text,
                                        @RequestParam(defaultValue = "", required = false) List<Integer> categories,
                                        @RequestParam(defaultValue = "false", required = false) Boolean paid,
                                        @RequestParam(required = false) @DateTimeFormat(pattern =
                                                DATE_TIME_FORMATTER) LocalDateTime rangeStart,
                                        @RequestParam(required = false) @DateTimeFormat(pattern =
                                                DATE_TIME_FORMATTER) LocalDateTime rangeEnd,
                                        @RequestParam(defaultValue = "false", required = false) Boolean onlyAvailable,
                                        @RequestParam(defaultValue = "EVENT_DATE", required = false) String sort,
                                        @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                        @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("                                                                           ");
        log.info("========================================");
        log.info("Получение списка событий, text {}, categories {}, paid {}, rangeStart {}, rangeEnd {}, " +
                        "onlyAvailable " +
                        "{}, sort {}, from {}, size {}", text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                sort, from, size);
        log.info("========================================");
        log.info("                                                                           ");
        return eventService.getEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                sort, from, size);
    }

    @GetMapping(path = "/events/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getEvent(@PathVariable Integer id, HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        String uri = request.getRequestURI();
        log.info("                                                                           ");
        log.info("========================================");
        log.info("Получение события по event id {}, ip {}, uri {} ", id, ip, uri);
        log.info("========================================");
        log.info("                                                                           ");
        return eventService.getEvent(id, ip, uri);
    }
}
