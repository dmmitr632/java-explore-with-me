package ru.practicum.ewm.service.adm;

import ru.practicum.ewm.dto.EventDto;

import java.util.List;

public interface EventAdminService {
    List<EventDto> getSelectedEvents(List<Integer> users, List<String> states, List<Integer> categories,
                                     String rangeStart, String rangeEnd, Integer from, Integer size);

    List<EventDto> approveOrRejectEvent(Integer eventId, EventDto eventDto);
}
