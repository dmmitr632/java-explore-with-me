package ru.practicum.ewm.service.priv;

import ru.practicum.ewm.dto.EventDto;
import ru.practicum.ewm.dto.EventShortDto;
import ru.practicum.ewm.dto.NewEventDto;
import ru.practicum.ewm.dto.UpdateEventUserRequest;

import java.util.Collection;

public interface EventPrivateService {
    Collection<EventShortDto> getEventsAddedByUser(Integer userId, Integer from, Integer size);

    EventDto editEventsUserRequest(Integer userId, Integer eventId);

    EventDto getEventsUserRequest(Integer userId, Integer eventId);

    EventDto editEventAddedByUser(UpdateEventUserRequest request, Integer userId);

    EventDto getEventAddedByUser(Integer userId, Integer eventId);

    EventDto addEvent(NewEventDto newEventDto, Integer userId);
}
