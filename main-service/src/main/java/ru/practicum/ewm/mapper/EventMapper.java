package ru.practicum.ewm.mapper;

import ru.practicum.ewm.dto.EventFullDto;
import ru.practicum.ewm.dto.EventShortDto;
import ru.practicum.ewm.dto.NewEventDto;
import ru.practicum.ewm.model.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventMapper {
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Event toEvent(NewEventDto newEventDto) {
        return new Event(newEventDto.getTitle(), newEventDto.getAnnotation(), newEventDto.getDescription(),
                LocalDateTime.parse(newEventDto.getEventDate(), timeFormat), newEventDto.getPaid(),
                newEventDto.getParticipantLimit(),
                newEventDto.getRequestModeration(), newEventDto.getLat(), newEventDto.getLon());
    }

    public static EventFullDto toEventFullDto(Event event) {
        EventFullDto eventFullDto = new EventFullDto(event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()), event.getCreatedOn(),
                event.getDescription(), event.getEventDate(), event.getId(),
                UserMapper.toUserShortDto(event.getInitiator()),
                event.getLat(), event.getLon(), event.getPaid(), event.getParticipantLimit(), event.getPublishedOn(),
                event.getTitle(),
                event.getRequestModeration(), event.getViews());
        if (event.getEventState() != null) {
            eventFullDto.setEventState(event.getEventState());
        }

        return eventFullDto;
    }

    public static EventShortDto toEventShortDto(Event event) {
        return new EventShortDto(event.getId(), event.getTitle(), event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()), event.getPaid(), event.getEventDate(),
                UserMapper.toUserShortDto(event.getInitiator()));
    }
}
