package ru.practicum.ewm.service.priv.implementation;

import ru.practicum.ewm.dto.UserRequestDto;
import ru.practicum.ewm.service.priv.UserRequestPrivateService;

import java.util.List;

public class UserRequestPrivateServiceImpl implements UserRequestPrivateService {
    @Override
    public List<UserRequestDto> getUserRequests(Integer userId) {
        return null;
    }

    @Override
    public UserRequestDto addUserRequest(Integer eventId, UserRequestDto eventDto) {
        return null;
    }

    @Override
    public UserRequestDto cancelUserRequest(Integer requestId, Integer userId) {
        return null;
    }
}
