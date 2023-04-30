package ru.practicum.ewm.service.priv;

import ru.practicum.ewm.dto.UserRequestDto;

import java.util.List;

public interface UserRequestPrivateService {
    List<UserRequestDto> getUserRequests(Integer userId);

    UserRequestDto addUserRequest(Integer eventId, UserRequestDto eventDto);

    UserRequestDto cancelUserRequest(Integer requestId, Integer userId);
}
