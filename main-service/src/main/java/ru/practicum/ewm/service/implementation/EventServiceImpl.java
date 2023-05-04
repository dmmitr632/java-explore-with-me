package ru.practicum.ewm.service.implementation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.*;
import ru.practicum.ewm.enumeration.EventState;
import ru.practicum.ewm.enumeration.RequestStatus;
import ru.practicum.ewm.enumeration.StateAction;
import ru.practicum.ewm.exception.BadRequestException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.PublishingException;
import ru.practicum.ewm.exception.TimeException;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.mapper.LocationMapper;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.Location;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.repository.*;
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
    private final UserRepository userRepository;

    private final LocationRepository locationRepository;

    private final ParticipationRequestRepository participationRequestRepository;

    public EventServiceImpl(EventRepository eventRepository, CategoryRepository categoryRepository,
                            UserRepository userRepository,
                            LocationRepository locationRepository,
                            ParticipationRequestRepository participationRequestRepository) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.participationRequestRepository = participationRequestRepository;
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
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Событие не найдено"));
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new TimeException("Нельзя подтвердить событие, если старт меньше, чем через час");
        }
        if (event.getState().equals(EventState.PUBLISHED) || event.getState().equals(EventState.CANCELED)) {
            throw new PublishingException("Событие уже подтверждено/отменено");
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
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя не существует"));
        return eventRepository.findAllByInitiatorId(userId, PageRequest.of(from / size, size))
                .stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto getSingleEventAddedByUser(Integer userId, Integer eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события не существует"));
        if (!event.getInitiator().getId().equals(userId)) {
            throw new BadRequestException("Пользователь не является инициатором события");
        }
        EventFullDto eventFullDto = EventMapper.toEventFullDto(event);
        eventFullDto.setConfirmedRequests(participationRequestRepository.countParticipationByEventIdAndStatus(eventFullDto.getId(),
                RequestStatus.CONFIRMED));
        return eventFullDto;
    }


    @Override
    public EventFullDto editEventAddedByUser(Integer userId,
                                             Integer eventId, UpdateEventUserRequest updateEventUserRequest) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя не существует"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события не существует"));
        if (!event.getInitiator().getId().equals(userId)) {
            throw new BadRequestException("Изменить событие может только его создатель");
        }
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new BadRequestException("Нельзя изменить уже опубликованное событие");
        }
        if (updateEventUserRequest.getAnnotation() != null) {
            event.setEventDate(LocalDateTime.parse(updateEventUserRequest.getAnnotation()));
        }
        if (updateEventUserRequest.getCategory() != null) {
            event.setCategory(categoryRepository.findById(updateEventUserRequest.getCategory())
                    .orElseThrow(() -> new NotFoundException("Такой категории не существует")));
        }
        if (updateEventUserRequest.getDescription() != null) {
            event.setDescription(updateEventUserRequest.getDescription());
        }
        if (updateEventUserRequest.getEventDate() != null) {
            LocalDateTime date = LocalDateTime.parse(updateEventUserRequest.getEventDate(),
                    dateTimeFormatter);
            if (date.isBefore(LocalDateTime.now().plusHours(2))) {
                throw new TimeException("Событие должно произойти как минимум через 2 часа");
            }
            event.setEventDate(date);
        }
        if (updateEventUserRequest.getPaid() != null) {
            event.setPaid(updateEventUserRequest.getPaid());
        }
        if (updateEventUserRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventUserRequest.getParticipantLimit());
        }
        if (updateEventUserRequest.getTitle() != null) {
            event.setTitle(updateEventUserRequest.getTitle());
        }
        if (event.getState().equals(EventState.CANCELED)) {
            event.setState(EventState.PENDING);
        }
        EventFullDto eventFullDto = EventMapper.toEventFullDto(eventRepository.save(event));
        eventFullDto.setConfirmedRequests(participationRequestRepository
                .countParticipationByEventIdAndStatus(eventFullDto.getId(),
                        RequestStatus.CONFIRMED));

        return eventFullDto;
    }


    @Override
    public EventFullDto addEvent(NewEventDto newEventDto, Integer userId) {
        Event event = EventMapper.toEventFromNewEventDto(newEventDto);
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Такого пользователя не " +
                "существует"));
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new BadRequestException("Событие должно произойти как минимум через 2 часа");
        }
        Location location = locationRepository.save(LocationMapper.toLocation(newEventDto.getLocation()));
        event.setCategory(categoryRepository.findById(newEventDto.getCategory())
                .orElseThrow(() -> new NotFoundException("Категории не существует")));
        event.setInitiator(user);
        event.setLocation(location);
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }


    // Public services

    @Override
    public List<EventFullDto> editEvent(Integer id, String remoteAddr) {
        return null;
    }

    @Override
    public List<EventFullDto> getEvents(List<Integer> users, List<String> states, List<Integer> categories,
                                        String rangeStart, String rangeEnd, Integer from, Integer size) {
        return null;
    }
}
