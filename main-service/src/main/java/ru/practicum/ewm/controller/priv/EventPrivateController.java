package ru.practicum.ewm.controller.priv;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.EventDto;
import ru.practicum.ewm.dto.EventShortDto;
import ru.practicum.ewm.dto.NewEventDto;
import ru.practicum.ewm.dto.UpdateEventUserRequest;
import ru.practicum.ewm.service.EventService;

import javax.validation.Valid;
import java.util.Collection;

public class EventPrivateController {
    private final EventService eventService;

    public EventPrivateController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping(path = "/users/{userId}/events")
    public Collection<EventShortDto> getEventsAddedByUser(@PathVariable Integer userId,
                                                          @RequestParam(defaultValue = "0") Integer from,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        return eventService.getEventsAddedByUser(userId, from, size);
    }

    @PostMapping(path = "/users/{userId}/events")
    public EventDto addEvent(@RequestBody @Valid NewEventDto newEventDto,
                             @PathVariable Integer userId) {
        return eventService.addEvent(newEventDto, userId);
    }

    @GetMapping(path = "/users/{userId}/events/{eventId}")
    public EventDto getEventAddedByUser(@PathVariable Integer userId, @PathVariable Integer eventId) {
        return eventService.getEventAddedByUser(userId, eventId);
    }


    @PatchMapping(path = "/users/{userId}/events/{eventId}")
    public EventDto editEventAddedByUser(@RequestBody @Valid UpdateEventUserRequest request,
                                         @PathVariable Integer userId, @PathVariable String eventId) {
        return eventService.editEventAddedByUser(request, userId);
    }


    @GetMapping(path = "/users/{userId}/events/{eventId}/requests")
    public EventDto getEventsUserRequest(@PathVariable Integer userId, @PathVariable Integer eventId) {
        return eventService.getEventsUserRequest(userId, eventId);
    }

    @PatchMapping(path = "/users/{userId}/events/{eventId}/requests")
    public EventDto editEventsUserRequest(@PathVariable Integer userId, @PathVariable Integer eventId) {
        return eventService.editEventsUserRequest(userId, eventId);
    }


}
