package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.*;
import ru.practicum.ewm.enumeration.EventState;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface EventService {

    Collection<EventFullDto> getSelectedEvents(List<Integer> users, List<EventState> states, List<Integer> categories,
                                               LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    EventFullDto approveOrRejectEvent(UpdateEventAdminRequest updateEventAdminRequest, Integer eventId);

    Collection<EventShortDto> getEventsAddedByUser(Integer userId, Integer from, Integer size);

    EventFullDto editEventAddedByUser(Integer userId, Integer eventId, UpdateEventUserRequest updateEventUserRequest);

    EventFullDto getSingleEventAddedByUser(Integer userId, Integer eventId);

    EventFullDto addEvent(NewEventDto newEventDto, Integer userId);

    EventFullDto getEvent(Integer eventId);

    List<EventFullDto> getEvents(String text, List<Integer> categories, Boolean paid, LocalDateTime rangeStart,
                                 LocalDateTime rangeEnd, Boolean available, String sort, Integer from, Integer size);
}
