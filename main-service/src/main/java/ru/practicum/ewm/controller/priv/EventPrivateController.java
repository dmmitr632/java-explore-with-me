package ru.practicum.ewm.controller.priv;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.practicum.ewm.dto.EventDto;
import ru.practicum.ewm.service.priv.EventPrivateService;

public class EventPrivateController {
    private final EventPrivateService eventPrivateService;

    public EventPrivateController(EventPrivateService eventPrivateService) {
        this.eventPrivateService = eventPrivateService;
    }

    @GetMapping(path = "/users/{userId}/events")
    public EventDto getEventsAddedByUser(@PathVariable Integer userId) {
        return eventPrivateService.getEventsAddedByUser(userId);
    }

    @PostMapping(path = "/users/{userId}/events")
    public EventDto addEvent(@PathVariable Integer userId) {
        return eventPrivateService.addEvent(userId);
    }

    @GetMapping(path = "/users/{userId}/events")
    public EventDto getEventAddedByUser(@PathVariable Integer userId) {
        return eventPrivateService.getEventAddedByUser(userId);
    }



    @PatchMapping(path = "/users/{userId}/events")
    public EventDto editEventAddedByUser(@PathVariable Integer userId) {
        return eventPrivateService.editEventAddedByUser(userId);
    }


    @GetMapping(path = "/users/{userId}/events/{eventId}/requests")
    public EventDto getEventsUserRequest(@PathVariable Integer userId, @PathVariable Integer eventId) {
        return eventPrivateService.getEventsUserRequest(userId, eventId);
    }

    @PatchMapping(path = "/users/{userId}/events/{eventId}/requests")
    public EventDto editEventsUserRequest(@PathVariable Integer userId, @PathVariable Integer eventId) {
        return eventPrivateService.editEventsUserRequest(userId, eventId);
    }



}
