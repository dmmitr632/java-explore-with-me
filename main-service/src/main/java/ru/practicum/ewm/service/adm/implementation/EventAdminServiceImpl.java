package ru.practicum.ewm.service.adm.implementation;

import ru.practicum.ewm.dto.EventDto;
import ru.practicum.ewm.service.adm.EventAdminService;

import java.util.List;

public class EventAdminServiceImpl implements EventAdminService {
    @Override
    public List<EventDto> getSelectedEvents(List<Integer> users,
                                            List<String> states,
                                            List<Integer> categories,
                                            String rangeStart,
                                            String rangeEnd,
                                            Integer from,
                                            Integer size) {
        return null;
    }

    @Override
    public List<EventDto> approveOrRejectEvent(Integer eventId, EventDto eventDto) {
        return null;
    }
}
