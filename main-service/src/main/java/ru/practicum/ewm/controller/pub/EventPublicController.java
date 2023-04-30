package ru.practicum.ewm.controller.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.dto.EventDto;
import ru.practicum.ewm.service.pub.EventPublicService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class EventPublicController {
    private final EventPublicService eventPublicService;

    public EventPublicController(EventPublicService eventPublicService) {
        this.eventPublicService = eventPublicService;
    }

    @GetMapping(path = "/events")
    public List<EventDto> getEvents(@RequestParam List<Integer> users,
                                    @RequestParam List<String> states,
                                    @RequestParam List<Integer> categories,
                                    @RequestParam String rangeStart,
                                    @RequestParam String rangeEnd,
                                    @RequestParam(defaultValue = "0") Integer from,
                                    @RequestParam(defaultValue = "10") Integer size) {
        return eventPublicService.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping(path = "/events/{id}")
    public List<EventDto> editEvent(@PathVariable Integer id,
                                    @Autowired HttpServletRequest servletRequest) {
        return eventPublicService.editEvent(id, servletRequest.getRemoteAddr());
    }
}
