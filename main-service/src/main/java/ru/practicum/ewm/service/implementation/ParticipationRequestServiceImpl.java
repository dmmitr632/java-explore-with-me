package ru.practicum.ewm.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.EventFullDto;
import ru.practicum.ewm.dto.ParticipationRequestDto;
import ru.practicum.ewm.dto.UpdateEventUserRequest;
import ru.practicum.ewm.enumeration.EventState;
import ru.practicum.ewm.enumeration.RequestStatus;
import ru.practicum.ewm.exception.BadRequestException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.ParticipationRequestMapper;
import ru.practicum.ewm.model.ParticipationRequest;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.ParticipationRequestRepository;
import ru.practicum.ewm.repository.UserRepository;
import ru.practicum.ewm.service.ParticipationRequestService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
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
    public Collection<ParticipationRequestDto> getUserParticipationRequests(Integer userId) {
        return participationRequestRepository.findAllByRequesterId(userId)
                .stream()
                .map(ParticipationRequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto addUserParticipationRequest(Integer userId, Integer eventId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Такого пользователя не " +
                "существует"));
        eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Такого события не " +
                "существует"));
        if (participationRequestRepository.findByRequesterIdAndEventId(userId, eventId) != null) {
            throw new BadRequestException("Такой запрос на участие уже существует");
        }
        ParticipationRequest participationRequest = ParticipationRequest
                .builder()
                .requester(userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Такого " +
                        "пользователя не существует")))
                .event(eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException("Такого события не " +
                        "существует")))
                .created(LocalDateTime.now())
                .status(RequestStatus.CONFIRMED)
                .build();

        if (!participationRequest.getEvent().getState().equals(EventState.PUBLISHED)) {
            throw new BadRequestException("Событие еще не опублкиовано");
        }
        if (userId.equals(participationRequest.getEvent().getInitiator().getId())) {
            throw new BadRequestException("Создатель заявки на участие и инициатор события не может быть одним и тем " +
                    "же человеком");
        }
        if (participationRequest.getEvent().getParticipantLimit() <= participationRequestRepository
                .countParticipationByEventIdAndStatus(eventId, RequestStatus.CONFIRMED)) {
            throw new BadRequestException("Максимальное число подтвержденных заявок на участие превышено");
        }
        if (participationRequest.getEvent().getRequestModeration()) {
            participationRequest.setStatus(RequestStatus.PENDING);
        }

        participationRequestRepository.save(participationRequest);

        return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
    }

    @Override
    public ParticipationRequestDto cancelUserParticipationRequest(Integer userId, Integer requestId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Такого пользователя не " +
                "существует"));
        ParticipationRequest participationRequest = participationRequestRepository.findById(requestId).orElseThrow(()
                -> new NotFoundException("Такой заявки на участие не существует"));
        if (!participationRequest.getRequester().getId().equals(userId)) {
            throw new BadRequestException("Нельзя отменить не свою заявку");
        }
        participationRequest.setStatus(RequestStatus.CANCELED);

        participationRequestRepository.save(participationRequest);

        return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
    }


    // Used by EventPrivateController

    @Override
    public Collection<ParticipationRequestDto> getUserRequestsForEvent(Integer userId, Integer eventId) {
        return null;
    }

    @Override
    public EventFullDto confirmUserRequestForEvent(Integer userId, Integer eventId,
                                                   UpdateEventUserRequest updateEventUserRequest) {
        return null;
    }

    @Override
    public EventFullDto rejectUserRequestForEvent(Integer userId, Integer eventId,
                                                  UpdateEventUserRequest updateEventUserRequest) {
        return null;
    }

}
