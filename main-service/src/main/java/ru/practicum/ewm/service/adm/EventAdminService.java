package ru.practicum.ewm.service.adm;

import ru.practicum.ewm.dto.EventFullDto;
import ru.practicum.ewm.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.enumeration.EventState;

import java.util.Collection;
import java.util.List;

public interface EventAdminService {
    Collection<EventFullDto> getSelectedEvents(List<Integer> users, List<EventState> states, List<Integer> categories,
                                               String rangeStart, String rangeEnd, Integer from, Integer size);

    EventFullDto approveOrRejectEvent(UpdateEventAdminRequest updateEventAdminRequest, Integer eventId);
}
