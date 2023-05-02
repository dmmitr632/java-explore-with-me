package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.ParticipationRequestDto;

import java.util.List;

public interface ParticipationRequestService {
    List<ParticipationRequestDto> getUserRequests(Integer userId);

    ParticipationRequestDto addUserRequest(Integer userId, Integer eventId);

    ParticipationRequestDto cancelUserRequest(Integer userId, Integer requestId);
}
