package ru.practicum.ewm.controller.adm;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.dto.EventDto;
import ru.practicum.ewm.service.pub.EventPublicService;

import java.util.List;

public class EventAdminController {
    private final EventPublicService eventPublicService;

    public EventAdminController(EventPublicService eventPublicService) {
        this.eventPublicService = eventPublicService;
    }

    @GetMapping(path = "/admin/events/{catId}")
    public List<EventDto> getEvents(@RequestParam List<Integer> users,
                                    @RequestParam List<String> states,
                                    @RequestParam List<Integer> categories,
                                    @RequestParam String rangeStart,
                                    @RequestParam String rangeEnd,
                                    @RequestParam(defaultValue = "0") Integer from,
                                    @RequestParam(defaultValue = "10") Integer size) {
        return eventPublicService.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping(path = "/admin/events/{eventId}")
    public List<EventDto> editEvent(@RequestParam Integer eventId,
                                    @RequestBody EventDto eventDto) {
        return eventPublicService.editEvent(eventId, eventDto);
    }
}
