package ru.practicum.ewm.controller.adm;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.EventFullDto;
import ru.practicum.ewm.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.enumeration.EventState;
import ru.practicum.ewm.service.EventService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class EventAdminController {
    private final EventService eventService;

    public EventAdminController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(path = "/admin/events")
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getSelectedEvents(@RequestParam List<Integer> users,
                                                @RequestParam List<EventState> states,
                                                @RequestParam List<Integer> categories,
                                                @RequestParam String rangeStart,
                                                @RequestParam String rangeEnd,
                                                @RequestParam(defaultValue = "0") Integer from,
                                                @RequestParam(defaultValue = "10") Integer size) {
        return new ArrayList<>(eventService.getSelectedEvents(users, states, categories, rangeStart, rangeEnd,
                from, size));
    }

    @PatchMapping(path = "/admin/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto approveOrRejectEvent(@RequestBody @Valid UpdateEventAdminRequest updateEventAdminRequest,
                                             @PathVariable(name = "eventId") Integer eventId) {
        return eventService.approveOrRejectEvent(updateEventAdminRequest, eventId);
    }
}
