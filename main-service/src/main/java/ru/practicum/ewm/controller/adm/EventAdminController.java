package ru.practicum.ewm.controller.adm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.EventFullDto;
import ru.practicum.ewm.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.enumeration.EventState;
import ru.practicum.ewm.service.EventService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.DateTimeFormatterConstant.DATE_TIME_FORMATTER;

@RestController
@Slf4j
public class EventAdminController {
    private final EventService eventService;

    public EventAdminController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/admin/events")
    public List<EventFullDto> getSelectedEventsAdmin(@RequestParam(defaultValue = "") List<Integer> users,
                                                     @RequestParam(defaultValue = "") List<EventState> states,
                                                     @RequestParam(defaultValue = "") List<Integer> categories,
                                                     @RequestParam(required = false) @DateTimeFormat(pattern =
                                                             DATE_TIME_FORMATTER) LocalDateTime rangeStart,
                                                     @RequestParam(required = false) @DateTimeFormat(pattern =
                                                             DATE_TIME_FORMATTER) LocalDateTime rangeEnd,
                                                     @RequestParam(defaultValue = "0") Integer from,
                                                     @RequestParam(defaultValue = "10") Integer size) {
        log.info("                                                                           ");
        log.info("========================================");
        log.info("Получение информации о событиях администратором {} {} {} {} {} {} {}", users, states, categories,
                rangeStart, rangeEnd, from, size);
        log.info("========================================");
        log.info("                                                                           ");
        return eventService.getSelectedEvents(users, states, categories, rangeStart, rangeEnd,
                from, size);
    }

    @PatchMapping("/admin/events/{eventId}")
    public EventFullDto approveOrRejectEventAdmin(@RequestBody @Valid UpdateEventAdminRequest updateEventAdminRequest,
                                                  @PathVariable(name = "eventId") Integer eventId) {
        log.info("Подтверждение или отмена события администратором, eventId {}, updateEventAdminRequest {}", eventId,
                updateEventAdminRequest);
        return eventService.approveOrRejectEvent(updateEventAdminRequest, eventId);
    }
}
