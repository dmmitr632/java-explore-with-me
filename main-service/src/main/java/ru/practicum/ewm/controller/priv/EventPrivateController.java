package ru.practicum.ewm.controller.priv;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.*;
import ru.practicum.ewm.service.EventService;
import ru.practicum.ewm.service.ParticipationRequestService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
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
    public List<ParticipationRequestDto> getUserRequestsForEvent(@PathVariable Integer userId,
                                                                 @PathVariable Integer eventId) {
        return new ArrayList<>(participationRequestService.getUserRequestsForEvent(userId, eventId));
    }

    @PatchMapping(path = "/users/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult confirmOrRejectUserRequestForEvent(@PathVariable Integer userId,
                                                                             @PathVariable Integer eventId,
                                                                             @RequestBody @Valid EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        return participationRequestService.confirmOrRejectUserRequestForEvent(userId, eventId,
                eventRequestStatusUpdateRequest);
    }

}
