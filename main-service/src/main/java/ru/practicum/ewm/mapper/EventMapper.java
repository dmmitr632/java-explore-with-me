package ru.practicum.ewm.mapper;

import ru.practicum.ewm.dto.EventFullDto;
import ru.practicum.ewm.dto.EventShortDto;
import ru.practicum.ewm.dto.NewEventDto;
import ru.practicum.ewm.enumeration.EventState;
import ru.practicum.ewm.model.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ru.practicum.ewm.mapper.CategoryMapper.toCategoryDto;

public class EventMapper {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static EventFullDto toEventFullDto(Event event) {
        return EventFullDto
                .builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(toCategoryDto(event.getCategory()))
                .createdOn(event.getCreatedOn().format(dateTimeFormatter))
                .description(event.getDescription())
                .eventDate(event.getEventDate().format(dateTimeFormatter))
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .location(LocationMapper.toLocationDto(event.getLocation()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }

    public static Event toEvent(EventFullDto eventFullDto) {
        return Event
                .builder()
                .id(eventFullDto.getId())
                .annotation(eventFullDto.getAnnotation())
                .category(CategoryMapper.toCategory(eventFullDto.getCategory()))
                .createdOn(LocalDateTime.parse(eventFullDto.getCreatedOn(), dateTimeFormatter))
                .description(eventFullDto.getDescription())
                .eventDate(LocalDateTime.parse(eventFullDto.getEventDate(), dateTimeFormatter))
                .paid(eventFullDto.getPaid())
                .participantLimit(eventFullDto.getParticipantLimit())
                .publishedOn(eventFullDto.getPublishedOn())
                .requestModeration(eventFullDto.getRequestModeration())
                .state(eventFullDto.getState())
                .title(eventFullDto.getTitle())
                .build();
    }

    public static Event toEventFromEventShortDto(EventShortDto eventShortDto) {
        return Event
                .builder()
                .id(eventShortDto.getId())
                .annotation(eventShortDto.getAnnotation())
                .category(CategoryMapper.toCategory(eventShortDto.getCategory()))
                .eventDate(LocalDateTime.parse(eventShortDto.getEventDate(), dateTimeFormatter))
                .paid(eventShortDto.getPaid())
                .title(eventShortDto.getTitle())
                .build();
    }

    public static Event toEventFromNewEventDto(NewEventDto newEventDto) {
        return Event
                .builder()
                .annotation(newEventDto.getAnnotation())
                .description(newEventDto.getDescription())
                .eventDate(LocalDateTime.parse(newEventDto.getEventDate(), dateTimeFormatter))
                .paid(newEventDto.getPaid())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.getRequestModeration())
                .title(newEventDto.getTitle())
                .state(EventState.PENDING)
                .createdOn(LocalDateTime.now())
                .build();
    }


    public static EventShortDto toEventShortDto(Event event) {
        return EventShortDto
                .builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(toCategoryDto(event.getCategory()))
                .eventDate(event.getEventDate().format(dateTimeFormatter))
                .initiator(UserMapper.toUserShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(event.getViews())
                .build();
    }
}


