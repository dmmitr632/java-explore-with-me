package ru.practicum.ewm.service.priv;

import ru.practicum.ewm.dto.EventDto;

public interface EventPrivateService {
    EventDto getEventsAddedByUser(Integer userId);

    EventDto editEventsUserRequest(Integer userId, Integer eventId);

    EventDto getEventsUserRequest(Integer userId, Integer eventId);

    EventDto editEventAddedByUser(Integer userId);

    EventDto getEventAddedByUser(Integer userId);

    EventDto addEvent(Integer userId);
}
