package ru.practicum.ewm.service.pub;

import ru.practicum.ewm.dto.EventDto;

import java.util.List;

public interface EventPublicService {
    List<EventDto> getEvents(List<Integer> users, List<String> states, List<Integer> categories,
                             String rangeStart, String rangeEnd, Integer from, Integer size);

    List<EventDto> editEvent(Integer eventId, EventDto eventDto);
}
