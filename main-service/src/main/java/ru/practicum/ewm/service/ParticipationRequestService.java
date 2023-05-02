package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.ParticipationRequestDto;

import java.util.Collection;

public interface ParticipationRequestService {
    Collection<ParticipationRequestDto> getUserRequests(Integer userId);

    ParticipationRequestDto addUserRequest(Integer userId, Integer eventId);

    ParticipationRequestDto cancelUserRequest(Integer userId, Integer requestId);
}
