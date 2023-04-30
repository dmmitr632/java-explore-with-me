package ru.practicum.ewm.service.pub.implementation;

import ru.practicum.ewm.dto.EventDto;
import ru.practicum.ewm.service.pub.EventPublicService;

import java.util.List;

public class EventPublicServiceImpl implements EventPublicService {


    @Override
    public List<EventDto> editEvent(Integer id, String remoteAddr) {
        return null;
    }

    @Override
    public List<EventDto> getEvents(List<Integer> users,
                                    List<String> states,
                                    List<Integer> categories,
                                    String rangeStart,
                                    String rangeEnd,
                                    Integer from,
                                    Integer size) {
        return null;
    }
}
