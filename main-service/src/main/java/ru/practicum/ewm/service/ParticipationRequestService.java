package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.EventFullDto;
import ru.practicum.ewm.dto.ParticipationRequestDto;
import ru.practicum.ewm.dto.UpdateEventUserRequest;

import java.util.Collection;

public interface ParticipationRequestService {

    // Used by ParticipationRequestPrivateController
    Collection<ParticipationRequestDto> getUserParticipationRequests(Integer userId);

    ParticipationRequestDto addUserParticipationRequest(Integer userId, Integer eventId);

    ParticipationRequestDto cancelUserParticipationRequest(Integer userId, Integer requestId);

    // Used by EventPrivateController

    Collection<ParticipationRequestDto> getUserRequestsForEvent(Integer userId, Integer eventId);

    EventFullDto confirmUserRequestForEvent(Integer userId, Integer eventId,
                                            UpdateEventUserRequest updateEventUserRequest);

    EventFullDto rejectUserRequestForEvent(Integer userId, Integer eventId,
                                           UpdateEventUserRequest updateEventUserRequest);
}
