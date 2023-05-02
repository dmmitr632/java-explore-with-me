package ru.practicum.ewm.controller.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.dto.EventFullDto;
import ru.practicum.ewm.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class EventPublicController {
    private final EventService eventService;

    public EventPublicController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(path = "/events")
    public List<EventFullDto> getEvents(@RequestParam List<Integer> users,
                                        @RequestParam List<String> states,
                                        @RequestParam List<Integer> categories,
                                        @RequestParam String rangeStart,
                                        @RequestParam String rangeEnd,
                                        @RequestParam(defaultValue = "0") Integer from,
                                        @RequestParam(defaultValue = "10") Integer size) {
        return eventService.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping(path = "/events/{id}")
    public List<EventFullDto> editEvent(@PathVariable Integer id,
                                        @Autowired HttpServletRequest servletRequest) {
        return eventService.editEvent(id, servletRequest.getRemoteAddr());
    }
}
