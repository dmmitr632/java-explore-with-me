package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.UserRequestDto;

import java.util.List;

public interface RequestService {
    List<UserRequestDto> getUserRequests(Integer userId);

    UserRequestDto addUserRequest(Integer eventId, UserRequestDto eventDto);

    UserRequestDto cancelUserRequest(Integer requestId, Integer userId);
}
