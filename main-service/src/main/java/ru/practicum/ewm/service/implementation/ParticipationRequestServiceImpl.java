package ru.practicum.ewm.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.dto.ParticipationRequestDto;
import ru.practicum.ewm.enumeration.EventState;
import ru.practicum.ewm.enumeration.RequestStatus;
import ru.practicum.ewm.exception.BadRequestException;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.ParticipationRequestMapper;
import ru.practicum.ewm.model.Event;
import ru.practicum.ewm.model.ParticipationRequest;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.ParticipationRequestRepository;
import ru.practicum.ewm.repository.UserRepository;
import ru.practicum.ewm.service.ParticipationRequestService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ParticipationRequestServiceImpl implements ParticipationRequestService {
    private final ParticipationRequestRepository participationRequestRepository;

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    public ParticipationRequestServiceImpl(ParticipationRequestRepository participationRequestRepository,
                                           UserRepository userRepository, EventRepository eventRepository) {
        this.participationRequestRepository = participationRequestRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }


    // Used by ParticipationRequestPrivateController

    @Override
    @Transactional
    public List<ParticipationRequestDto> getUserParticipationRequests(Integer userId) {
        log.info("                                                                           ");
        log.info("========================================");
        log.info("ParticipationRequestServiceImpl getUserParticipationRequests, userId {}", userId);
        log.info("========================================");
        log.info("                                                                           ");
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя не существует"));

        return participationRequestRepository.findAllByRequesterId(userId)
                .stream()
                .map(ParticipationRequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto addUserParticipationRequest(Integer userId, Integer eventId) {

        log.info("                                                                           ");
        log.info("========================================");
        log.info("ParticipationRequestServiceImpl addUserParticipationRequest, userId {}, eventId {}", userId, eventId);
        log.info("========================================");
        log.info("                                                                           ");

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователя не " +
                "существует"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("События не " +
                "существует"));
        if (participationRequestRepository.findByRequesterIdAndEventId(userId, eventId) != null) {
            throw new ConflictException("Запрос на участие уже существует");
        }
        ParticipationRequest participationRequest = ParticipationRequest
                .builder()
                .requester(user)
                .event(event)
                .created(LocalDateTime.now())
                .status(RequestStatus.CONFIRMED)
                .build();

        if (!participationRequest.getEvent().getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException("Событие еще не опублкиовано");
        }
        if (userId.equals(participationRequest.getEvent().getInitiator().getId())) {
            throw new ConflictException("Создатель заявки на участие и инициатор события не может быть одним и тем " +
                    "же человеком");
        }
        if (participationRequest.getEvent().getParticipantLimit() <= participationRequestRepository
                .countParticipationByEventIdAndStatus(eventId, RequestStatus.CONFIRMED)) {
            throw new ConflictException("Максимальное число подтвержденных заявок на участие превышено");
        }
        if (participationRequest.getEvent().getRequestModeration()) {
            participationRequest.setStatus(RequestStatus.PENDING);
        }

        participationRequestRepository.save(participationRequest);

        return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelUserParticipationRequest(Integer userId, Integer requestId) {


        log.info("                                                                           ");
        log.info("========================================");
        log.info("ParticipationRequestServiceImpl cancelUserParticipationRequest, userId {}, requestId {}", userId,
                requestId);
        log.info("========================================");
        log.info("                                                                           ");


        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Такого пользователя не " +
                "существует"));
        ParticipationRequest participationRequest = participationRequestRepository.findById(requestId).orElseThrow(()
                -> new NotFoundException("Заявки на участие не существует"));
        if (!participationRequest.getRequester().getId().equals(userId)) {
            throw new BadRequestException("Нельзя отменить не свою заявку");
        }
        participationRequest.setStatus(RequestStatus.CANCELED);

        participationRequestRepository.save(participationRequest);

        return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
    }


    // Used by EventPrivateController

    @Override
    @Transactional
    public List<ParticipationRequestDto> getUserRequestsForEvent(Integer userId, Integer eventId) {

        log.info("                                                                           ");
        log.info("========================================");
        log.info("ParticipationRequestServiceImpl getUserRequestsForEvent, userId {}, eventId {}", userId, eventId);
        log.info("========================================");
        log.info("                                                                           ");

        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя не существует"));
        eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события не существует"));

//        participationRequestRepository.findAllByRequesterIdAndEventId


        return participationRequestRepository.findAllByEventInitiatorIdAndEventId(userId, eventId)
                .stream().map(ParticipationRequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public EventRequestStatusUpdateResult confirmOrRejectUserRequestForEvent(Integer userId, Integer eventId,
                                                                             EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {


        log.info("                                                                           ");
        log.info("========================================");
        log.info("ParticipationRequestServiceImpl confirmOrRejectUserRequestForEvent, userId {}, eventId {}, " +
                "eventRequestStatusUpdateRequest {}", userId, eventId, eventRequestStatusUpdateRequest);
        log.info("========================================");
        log.info("                                                                           ");


        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Пользователя не " +
                "существует"));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("События не " +
                "существует"));
        List<ParticipationRequest> participationRequests =
                participationRequestRepository.findByIdInOrderById(eventRequestStatusUpdateRequest.getRequestIds());

        List<ParticipationRequestDto> confirmedRequests = new ArrayList<>();
        List<ParticipationRequestDto> rejectedRequests = new ArrayList<>();
        log.info("пустые массивы созданы");


        for (ParticipationRequest participationRequest : participationRequests) {
            if (participationRequest.getEvent().equals(event)) {
                if (participationRequest.getStatus() == RequestStatus.PENDING) {

                    List<ParticipationRequest> requestsForEvent =
                            participationRequestRepository.findByEventAndStatus(event, RequestStatus.CONFIRMED);
                    if (requestsForEvent.size() == event.getParticipantLimit()) {
                        throw new ConflictException("Превышен лимит участников");
                    }
                    if (((!event.getRequestModeration()) || (event.getParticipantLimit() == 0))
                            && eventRequestStatusUpdateRequest.getStatus() == RequestStatus.CONFIRMED) {
                        participationRequest.setStatus(RequestStatus.CONFIRMED);
                    } else {
                        log.info("eventRequestStatusUpdateRequest.getStatus() {}",
                                eventRequestStatusUpdateRequest.getStatus());
                        if (eventRequestStatusUpdateRequest.getStatus() == RequestStatus.CONFIRMED) {
                            participationRequest.setStatus(RequestStatus.CONFIRMED);
                        } else if (eventRequestStatusUpdateRequest.getStatus() == RequestStatus.REJECTED) {
                                participationRequest.setStatus(RequestStatus.REJECTED);
                        } else if (eventRequestStatusUpdateRequest.getStatus() == RequestStatus.PENDING) {
                            participationRequest.setStatus(RequestStatus.CONFIRMED);
                        } else {
                            throw new ConflictException("Нельзя изменить отмененный запрос на участие");
                        }
                    }
                    participationRequestRepository.save(participationRequest);
                    log.info("Сохранение в репозиторий");
                }
                if (participationRequest.getStatus() == RequestStatus.CONFIRMED) {
                    if (eventRequestStatusUpdateRequest.getStatus() == RequestStatus.REJECTED) {
                        throw new ConflictException("Попытка отменить уже подтвержденную заявку");
                    }
                    log.info("CONFIRMED, participationRequest {} ", participationRequest);
                    confirmedRequests.add(ParticipationRequestMapper.toParticipationRequestDto(participationRequest));
                } else if (participationRequest.getStatus() == RequestStatus.REJECTED) {
                    log.info("REJECTED, participationRequest {} ", participationRequest);
                    rejectedRequests.add(ParticipationRequestMapper.toParticipationRequestDto(participationRequest));
                } else {
                    log.info("Что-то не так");
                }
            }
        }
        EventRequestStatusUpdateResult eventRequestStatusUpdateResult =
                new EventRequestStatusUpdateResult(confirmedRequests, rejectedRequests);
        log.info("                                                                           ");
        log.info("========================================");
        log.info("ParticipationRequestServiceImpl confirmOrRejectUserRequestForEvent, eventRequestStatusUpdateResult " +
                "{}", eventRequestStatusUpdateResult);
        log.info("========================================");
        log.info("                                                                           ");
        return eventRequestStatusUpdateResult;
    }

}
