package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.*;
import ru.practicum.ewm.enumeration.EventState;

import java.util.Collection;
import java.util.List;

public interface EventService {

    Collection<EventFullDto> getSelectedEvents(List<Integer> users, List<EventState> states, List<Integer> categories,
                                               String rangeStart, String rangeEnd, Integer from, Integer size);

    EventFullDto approveOrRejectEvent(UpdateEventAdminRequest updateEventAdminRequest, Integer eventId);

    Collection<EventShortDto> getEventsAddedByUser(Integer userId, Integer from, Integer size);

    EventDto editEventsUserRequest(Integer userId, Integer eventId);

    EventDto getEventsUserRequest(Integer userId, Integer eventId);

    EventDto editEventAddedByUser(UpdateEventUserRequest request, Integer userId);

    EventDto getEventAddedByUser(Integer userId, Integer eventId);

    EventDto addEvent(NewEventDto newEventDto, Integer userId);

    List<EventDto> editEvent(Integer id, String remoteAddr);

    List<EventDto> getEvents(List<Integer> users,
                             List<String> states,
                             List<Integer> categories,
                             String rangeStart,
                             String rangeEnd,
                             Integer from,
                             Integer size);


}
