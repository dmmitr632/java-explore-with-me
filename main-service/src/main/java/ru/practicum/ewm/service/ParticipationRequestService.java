package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.dto.ParticipationRequestDto;

import java.util.Collection;

public interface ParticipationRequestService {

    // Used by ParticipationRequestPrivateController
    Collection<ParticipationRequestDto> getUserParticipationRequests(Integer userId);

    ParticipationRequestDto addUserParticipationRequest(Integer userId, Integer eventId);

    ParticipationRequestDto cancelUserParticipationRequest(Integer userId, Integer requestId);

    // Used by EventPrivateController

    Collection<ParticipationRequestDto> getUserRequestsForEvent(Integer userId, Integer eventId);

    EventRequestStatusUpdateResult confirmOrRejectUserRequestForEvent(Integer userId, Integer eventId,
                                                                      EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);
}
