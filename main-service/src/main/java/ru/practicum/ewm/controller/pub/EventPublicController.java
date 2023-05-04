package ru.practicum.ewm.controller.pub;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.dto.EventFullDto;
import ru.practicum.ewm.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

public class EventPublicController {
    private final EventService eventService;

    public EventPublicController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(path = "/events")
    public List<EventFullDto> getEvents(@RequestParam(name = "text", required = false) String text,
                                        @RequestParam(name = "categories", required = false) List<Integer> categories,
                                        @RequestParam(name = "paid", required = false) Boolean paid,
                                        @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                        @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                        @RequestParam(name = "onlyAvailable", required = false) Boolean available,
                                        @RequestParam(name = "sort", required = false) String sort,
                                        @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                        @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return eventService.getEvents(text, categories, paid, rangeStart, rangeEnd, available,
                sort, from, size);
    }

    @GetMapping(path = "/events/{id}")
    public EventFullDto getEvent(@PathVariable Integer id) {
        return eventService.getEvent(id);
    }
}
