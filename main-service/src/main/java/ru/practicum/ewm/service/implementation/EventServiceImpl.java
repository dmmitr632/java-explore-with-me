package ru.practicum.ewm.service.implementation;

import lombok.extern.slf4j.Slf4j;
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
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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

    @Override
    @Transactional
    public Collection<EventFullDto> getSelectedEvents(List<Integer> usersIds, List<EventState> states,
                                                      List<Integer> categories,
                                                      LocalDateTime start, LocalDateTime end, Integer from,
                                                      Integer size) {

        log.info("---------------------------------------------------------------------------");
        log.info("EventServiceImpl getSelectedEvents: usersIds {}, states {}, categories {}, start {}, end {}, from " +
                "{}, size {}", usersIds, states, categories, start, end, from, size);
        log.info("---------------------------------------------------------------------------");

        Pageable pageable = PageRequest.of(from, size);

//        Page<Event> events =
//                eventRepository.getSelectedEvents(
//                        usersIds, states, categories,
//                        start, end, pageable);

        Page<Event> events =
                eventRepository.findByInitiatorIdInAndStateInAndCategoryIdInAndEventDateIsAfterAndEventDateIsBefore(
                        usersIds, states, categories,
                        start, end, pageable);

        return events.stream().map(EventMapper::toEventFullDto).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public EventFullDto approveOrRejectEvent(UpdateEventAdminRequest updateEvent, Integer eventId) {

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Событие не найдено"));
        event.setPublishedOn(LocalDateTime.now());
        log.info("---------------------------------------------------------------------------");
        log.info("EventServiceImpl approveOrRejectEvent, event {}", event);
        log.info("---------------------------------------------------------------------------");
        if (event.getEventDate().isBefore(event.getPublishedOn().plusHours(1))) {
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
            event.setEventDate(updateEvent.getEventDate());
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
    @Transactional
    public Collection<EventShortDto> getEventsAddedByUser(Integer userId, Integer from, Integer size) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя не существует"));
        return eventRepository.findAllByInitiatorId(userId, PageRequest.of(from / size, size))
                .stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
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
    @Transactional
    public EventFullDto editEventAddedByUser(Integer userId,
                                             Integer eventId, UpdateEventUserRequest updateEventUserRequest) {

        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя не существует"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события не существует"));

        log.info("---------------------------------------------------------------------------");
        log.info("EventServiceImpl editEventAddedByUser, event.getEventDate {}", event.getEventDate());
        log.info("---------------------------------------------------------------------------");

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
            LocalDateTime localDateTime = updateEventUserRequest.getEventDate();
            log.info("---------------------------------------------------------------------------");
            log.info("EventServiceImpl editEventAddedByUser, dateTime {}", localDateTime);
            log.info("---------------------------------------------------------------------------");
            if (localDateTime.isBefore(LocalDateTime.now().plusHours(2))) {
                throw new TimeException("Событие должно произойти как минимум через 2 часа");
            }
            event.setEventDate(localDateTime);
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
    @Transactional
    public EventFullDto addEvent(NewEventDto newEventDto, Integer userId) {
        log.info("---------------------------------------------------------------------------");
        log.info("EventServiceImpl addEvent, добавление события, newEventDto {}, userId {}", newEventDto, userId);
        log.info("---------------------------------------------------------------------------");
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
        log.info("---------------------------------------------------------------------------");
        log.info("EventServiceImpl addEvent, cохранение event {}", event);
        log.info("---------------------------------------------------------------------------");
        eventRepository.save(event);
        return EventMapper.toEventFullDto(event);
    }


    // Public services

    @Override
    @Transactional
    public EventFullDto getEvent(Integer eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Событие не найдено"));
        if (!event.getState().equals(EventState.PUBLISHED)) {
            throw new BadRequestException("Пока событие не опубликовано просмотр невозможен");
        }
        event.setViews(event.getViews() + 1);
        EventFullDto eventFullDto = EventMapper.toEventFullDto(event);
        eventFullDto.setConfirmedRequests(
                participationRequestRepository.countParticipationByEventIdAndStatus(eventFullDto.getId(),
                        RequestStatus.CONFIRMED));
        return eventFullDto;
    }

    @Override
    @Transactional
    public List<EventFullDto> getEvents(String text, List<Integer> categories, Boolean paid, LocalDateTime start,
                                        LocalDateTime end, Boolean available, String sort, Integer from, Integer size) {

        log.info("---------------------------------------------------------------------------");
        log.info("EventServiceImpl getEvents");
        log.info("---------------------------------------------------------------------------");

        Pageable pageable = PageRequest.of(from, size);


        List<Event> events = eventRepository.getEvents(text.toLowerCase(), categories, paid, EventState.PUBLISHED,
                start, end, pageable);


        log.info("---------------------------------------------------------------------------");
        log.info("EventServiceImpl получен список events из eventRepository, {}", events);
        log.info("---------------------------------------------------------------------------");


        List<EventFullDto> eventFullDtoList =
                events.stream().map(EventMapper::toEventFullDto).collect(Collectors.toList());


        eventFullDtoList.forEach(e -> e.setConfirmedRequests(
                participationRequestRepository.countParticipationByEventIdAndStatus(
                        e.getId(), RequestStatus.CONFIRMED))
        );


        if (sort != null) {
            if (sort.equals("EVENT_DATE")) {
                events.sort((Comparator.comparing(Event::getEventDate)));
            } else if (sort.equals("VIEWS")) {
                events.sort(Comparator.comparing(Event::getViews));
            } else {
                throw new BadRequestException("Неверный тип сортировки");
            }
        }

        eventFullDtoList.forEach(e -> {
            if (e.getViews() == null)
                e.setViews(0);
        });
        eventFullDtoList.forEach(e -> e.setViews(e.getViews() + 1));
        events.forEach(e -> {
            if (e.getViews() == null)
                e.setViews(0);
        });
        events.forEach(e -> e.setViews(e.getViews() + 1));

        return eventFullDtoList;
    }
}

