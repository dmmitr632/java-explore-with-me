package ru.practicum.ewm.service.priv.implementation;

import ru.practicum.ewm.dto.EventDto;
import ru.practicum.ewm.dto.EventShortDto;
import ru.practicum.ewm.dto.NewEventDto;
import ru.practicum.ewm.dto.UpdateEventUserRequest;
import ru.practicum.ewm.service.priv.EventPrivateService;

import java.util.Collection;

public class EventPrivateServiceImpl implements EventPrivateService {
    @Override
    public Collection<EventShortDto> getEventsAddedByUser(Integer userId, Integer from, Integer size) {
        return null;
    }

    @Override
    public EventDto editEventsUserRequest(Integer userId, Integer eventId) {
        return null;
    }

    @Override
    public EventDto getEventsUserRequest(Integer userId, Integer eventId) {
        return null;
    }

    @Override
    public EventDto editEventAddedByUser(UpdateEventUserRequest request, Integer userId) {
        return null;
    }

    @Override
    public EventDto getEventAddedByUser(Integer userId, Integer eventId) {
        return null;
    }

    @Override
    public EventDto addEvent(NewEventDto newEventDto, Integer userId) {
        return null;
    }
}
