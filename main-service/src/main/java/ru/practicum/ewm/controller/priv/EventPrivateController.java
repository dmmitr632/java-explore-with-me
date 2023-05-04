package ru.practicum.ewm.controller.priv;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.EventFullDto;
import ru.practicum.ewm.dto.EventShortDto;
import ru.practicum.ewm.dto.NewEventDto;
import ru.practicum.ewm.dto.UpdateEventUserRequest;
import ru.practicum.ewm.service.EventService;
import ru.practicum.ewm.service.ParticipationRequestService;

import javax.validation.Valid;
import java.util.Collection;

public class EventPrivateController {
    private final EventService eventService;
    private final ParticipationRequestService participationRequestService;

    public EventPrivateController(EventService eventService, ParticipationRequestService participationRequestService) {
        this.eventService = eventService;
        this.participationRequestService = participationRequestService;
    }

    @GetMapping(path = "/users/{userId}/events")
    public Collection<EventShortDto> getEventsAddedByUser(@PathVariable Integer userId,
                                                          @RequestParam(defaultValue = "0") Integer from,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        return eventService.getEventsAddedByUser(userId, from, size);
    }

    @PostMapping(path = "/users/{userId}/events")
    public EventFullDto addEvent(@RequestBody @Valid NewEventDto newEventDto,
                                 @PathVariable Integer userId) {
        return eventService.addEvent(newEventDto, userId);
    }

    @GetMapping(path = "/users/{userId}/events/{eventId}")
    public EventFullDto getSingleEventAddedByUser(@PathVariable Integer userId, @PathVariable Integer eventId) {
        return eventService.getSingleEventAddedByUser(userId, eventId);
    }


    @PatchMapping(path = "/users/{userId}/events/{eventId}")
    public EventFullDto editEventAddedByUser(@PathVariable Integer userId, @PathVariable Integer eventId,
                                             @RequestBody @Valid UpdateEventUserRequest updateEventUserRequest) {
        return eventService.editEventAddedByUser(userId, eventId, updateEventUserRequest);
    }

    // ParticipationRequestService
    @GetMapping(path = "/users/{userId}/events/{eventId}/requests")
    public EventFullDto getUserRequestsForEvent(@PathVariable Integer userId, @PathVariable Integer eventId) {
        return participationRequestService.getUserRequestsForEvent(userId, eventId);
    }

    @PatchMapping(path = "/users/{userId}/events/{eventId}/requests")
    public EventFullDto confirmUserRequestForEvent(@PathVariable Integer userId, @PathVariable Integer eventId,
                                                   @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        return participationRequestService.confirmUserRequestForEvent(userId, eventId, updateEventUserRequest);
    }

    @PatchMapping(path = "/users/{userId}/events/{eventId}/requests")
    public EventFullDto rejectUserRequestForEvent(@PathVariable Integer userId, @PathVariable Integer eventId,
                                                  @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        return participationRequestService.rejectUserRequestForEvent(userId, eventId, updateEventUserRequest);
    }

}
