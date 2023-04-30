package ru.practicum.ewm.controller.adm;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.EventDto;
import ru.practicum.ewm.service.adm.EventAdminService;

import java.util.List;

public class EventAdminController {
    private final EventAdminService eventAdminService;

    public EventAdminController(EventAdminService eventAdminService) {
        this.eventAdminService = eventAdminService;
    }

    @GetMapping(path = "/admin/events")
    public List<EventDto> getSelectedEvents(@RequestParam List<Integer> users,
                                            @RequestParam List<String> states,
                                            @RequestParam List<Integer> categories,
                                            @RequestParam String rangeStart,
                                            @RequestParam String rangeEnd,
                                            @RequestParam(defaultValue = "0") Integer from,
                                            @RequestParam(defaultValue = "10") Integer size) {
        return eventAdminService.getSelectedEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping(path = "/admin/events/{eventId}")
    public List<EventDto> approveOrRejectEvent(@PathVariable Integer eventId,
                                               @RequestBody EventDto eventDto) {
        return eventAdminService.approveOrRejectEvent(eventId, eventDto);
    }
}
