package ru.practicum.ewm.service.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.*;
import ru.practicum.ewm.enumeration.EventState;
import ru.practicum.ewm.enumeration.StateAction;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.PublishingException;
import ru.practicum.ewm.exception.TimeException;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.repository.CategoryRepository;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.service.EventService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {


    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    public EventServiceImpl(EventRepository eventRepository, CategoryRepository categoryRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
    }


    //Admin services

    public Collection<EventFullDto> getSelectedEvents(List<Integer> usersIds, List<EventState> states,
                                                      List<Integer> categories,
                                                      String start, String end, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        if (start != null) {
            startTime = LocalDateTime.parse(start, dateTimeFormatter);
        }
        if (end != null) {
            endTime = LocalDateTime.parse(end, dateTimeFormatter);
        }
        Page<Event> events = eventRepository.getSelectedEvents(usersIds, states, categories, startTime,
                endTime, pageable);
        return events.stream().map(EventMapper::toEventFullDto).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public EventFullDto approveOrRejectEvent(UpdateEventAdminRequest updateEvent, Integer eventId) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Event not found"));
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new TimeException("Cannot publish event if start in less than 1 hour");
        }
        if (event.getState().equals(EventState.PUBLISHED) || event.getState().equals(EventState.CANCELED)) {
            throw new PublishingException("Already published/canceled");
        }
        if (updateEvent.getTitle() != null) {

            event.setTitle(updateEvent.getTitle());
        }
        if (updateEvent.getAnnotation() != null) {
            event.setAnnotation(updateEvent.getAnnotation());
        }
        if (updateEvent.getDescription() != null) {
            event.setDescription(updateEvent.getDescription());
        }
        if (updateEvent.getEventDate() != null) {
            event.setEventDate(LocalDateTime.parse(updateEvent.getEventDate(), dateTimeFormatter));
        }
        if (updateEvent.getCategory() != null) {
            event.setCategory(categoryRepository.findById(updateEvent.getCategory()).get());
        }
        if (updateEvent.getPaid() != null) {

            event.setPaid(updateEvent.getPaid());
        }
        if (updateEvent.getParticipantLimit() != null) {

            event.setParticipantLimit(updateEvent.getParticipantLimit());
        }
        if (updateEvent.getLocation() != null) {
            event.setLocation(updateEvent.getLocation());
        }
        if (updateEvent.getRequestModeration() != null) {

            event.setRequestModeration(updateEvent.getRequestModeration());
        }
        if (updateEvent.getStateAction() != null) {
            if (updateEvent.getStateAction() == StateAction.SEND_TO_REVIEW) {

                event.setState(EventState.PENDING);
            } else if (updateEvent.getStateAction() == StateAction.CANCEL_REVIEW
                    || updateEvent.getStateAction() == StateAction.REJECT_EVENT) {
                event.setState(EventState.CANCELED);
            } else {
                event.setState(EventState.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
            }
        }
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    // Private Services


    @Override
    public Collection<EventShortDto> getEventsAddedByUser(Integer userId, Integer from, Integer size) {
        return eventRepository.findAllByInitiatorId(userId, PageRequest.of(from/size, size))
                .stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
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


    // Public services

    @Override
    public List<EventDto> editEvent(Integer id, String remoteAddr) {
        return null;
    }

    @Override
    public List<EventDto> getEvents(List<Integer> users, List<String> states, List<Integer> categories,
                                    String rangeStart, String rangeEnd, Integer from, Integer size) {
        return null;
    }
}
