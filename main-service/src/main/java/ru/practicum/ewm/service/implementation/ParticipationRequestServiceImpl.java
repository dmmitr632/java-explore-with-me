package ru.practicum.ewm.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.ParticipationRequestDto;
import ru.practicum.ewm.mapper.ParticipationRequestMapper;
import ru.practicum.ewm.repository.ParticipationRequestRepository;
import ru.practicum.ewm.service.ParticipationRequestService;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ParticipationRequestServiceImpl implements ParticipationRequestService {
    private final ParticipationRequestRepository participationRequestRepository;

    public ParticipationRequestServiceImpl(ParticipationRequestRepository participationRequestRepository) {
        this.participationRequestRepository = participationRequestRepository;
    }

    @Override
    @Transactional
    public Collection<ParticipationRequestDto> getUserRequests(Integer userId) {
        return participationRequestRepository.findAllByRequesterId(userId)
                .stream()
                .map(ParticipationRequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    @Override
    public ParticipationRequestDto addUserRequest(Integer userId, Integer eventId) {

        return null;
    }

    @Override
    public ParticipationRequestDto cancelUserRequest(Integer userId, Integer requestId) {
        return null;
    }

}
