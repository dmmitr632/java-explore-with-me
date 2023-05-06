package ru.practicum.ewm.controller.priv;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.*;
import ru.practicum.ewm.service.EventService;
import ru.practicum.ewm.service.ParticipationRequestService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@Slf4j
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
        log.info("                                                                           ");
        log.info("========================================");
        log.info("Получение пользователем добавленных им событий, userId {}, from {}, size {}", userId, from, size);
        log.info("========================================");
        log.info("                                                                           ");
        return eventService.getEventsAddedByUser(userId, from, size);
    }

    @PostMapping(path = "/users/{userId}/events")
    public EventFullDto addEvent(@RequestBody @Valid NewEventDto newEventDto,
                                 @PathVariable Integer userId) {
        log.info("                                                                           ");
        log.info("========================================");
        log.info("Добавление пользователем события, userId {}, newEventDto {}", userId, newEventDto);
        log.info("========================================");
        log.info("                                                                           ");
        return eventService.addEvent(newEventDto, userId);
    }

    @GetMapping(path = "/users/{userId}/events/{eventId}")
    public EventFullDto getSingleEventAddedByUser(@PathVariable Integer userId, @PathVariable Integer eventId) {
        log.info("                                                                           ");
        log.info("========================================");
        log.info("Получение пользователем события, userId {}, eventId {}", userId, eventId);
        log.info("========================================");
        log.info("                                                                           ");
        return eventService.getSingleEventAddedByUser(userId, eventId);
    }


    @PatchMapping(path = "/users/{userId}/events/{eventId}")
    public EventFullDto editEventAddedByUser(@PathVariable Integer userId, @PathVariable Integer eventId,
                                             @RequestBody @Valid UpdateEventUserRequest updateEventUserRequest) {
        log.info("                                                                           ");
        log.info("========================================");
        log.info("Изменение пользователем события, userId {}, eventId {}, updateEventUserRequest {}", userId, eventId,
                updateEventUserRequest);
        log.info("========================================");
        log.info("                                                                           ");
        return eventService.editEventAddedByUser(userId, eventId, updateEventUserRequest);
    }

    // ParticipationRequestService
    @GetMapping(path = "/users/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getUserRequestsForEvent(@PathVariable Integer userId,
                                                                 @PathVariable Integer eventId) {
        log.info("                                                                           ");
        log.info("========================================");
        log.info("Получение пользователем списка заявок на события, userId {}, eventId {}",
                userId, eventId);
        log.info("========================================");
        log.info("                                                                           ");
        return new ArrayList<>(participationRequestService.getUserRequestsForEvent(userId, eventId));
    }

    @PatchMapping(path = "/users/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult confirmOrRejectUserRequestForEvent(@PathVariable Integer userId,
                                                                             @PathVariable Integer eventId,
                                                                             @RequestBody @Valid EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        log.info("                                                                           ");
        log.info("========================================");
        log.info("Подтверждение или запрет пользователем заявки на участие в событии, userId {}, eventId {}, " +
                        "eventRequestStatusUpdateRequest {}",
                userId, eventId, eventRequestStatusUpdateRequest);
        log.info("========================================");
        log.info("                                                                           ");
        return participationRequestService.confirmOrRejectUserRequestForEvent(userId, eventId,
                eventRequestStatusUpdateRequest);
    }

}
