package ru.practicum.ewm.controller.adm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.EventFullDto;
import ru.practicum.ewm.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.enumeration.EventState;
import ru.practicum.ewm.service.EventService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static ru.practicum.ewm.DateTimeFormatterConstant.DATE_TIME_FORMATTER;

@RestController
@Slf4j
public class EventAdminController {
    private final EventService eventService;

    public EventAdminController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(path = "/admin/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getSelectedEvents(@RequestParam(required = false) List<Integer> users,
                                                @RequestParam(required = false) List<EventState> states,
                                                @RequestParam(required = false) List<Integer> categories,
                                                @RequestParam(required = false) @DateTimeFormat(pattern =
                                                        DATE_TIME_FORMATTER) LocalDateTime rangeStart,
                                                @RequestParam(required = false)@DateTimeFormat(pattern =
                                                        DATE_TIME_FORMATTER) LocalDateTime rangeEnd,
                                                @RequestParam(defaultValue = "0") Integer from,
                                                @RequestParam(defaultValue = "10") Integer size) {
        log.info("Получение информации о событиях администратором {} {} {} {} {} {} {}", users, states, categories,
                rangeStart,
                rangeEnd, from, size);
        return new ArrayList<>(eventService.getSelectedEvents(users, states, categories, rangeStart, rangeEnd,
                from, size));
    }

    @PatchMapping(path = "/admin/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto approveOrRejectEvent(@RequestBody @Valid UpdateEventAdminRequest updateEventAdminRequest,
                                             @PathVariable(name = "eventId") Integer eventId) {
        log.info("Подтверждение или отмена события администратором, eventId {}, updateEventAdminRequest {}", eventId,
                updateEventAdminRequest);
        return eventService.approveOrRejectEvent(updateEventAdminRequest, eventId);
    }
}
