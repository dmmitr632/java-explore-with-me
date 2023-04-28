package ru.practicum.ewm.service.priv.implementation;

import ru.practicum.ewm.dto.UserRequestDto;
import ru.practicum.ewm.service.priv.UserRequestPrivateService;

import java.util.List;

public class UserRequestPrivateServiceImpl implements UserRequestPrivateService {
    @Override
    public List<UserRequestDto> getUserRequests(List<Integer> users, List<String> states, List<Integer> categories, String rangeStart, String rangeEnd, Integer from, Integer size) {
        return null;
    }

    @Override
    public List<UserRequestDto> addUserRequest(Integer eventId, UserRequestDto eventDto) {
        return null;
    }

    @Override
    public List<UserRequestDto> cancelUserRequest(Integer eventId, UserRequestDto eventDto) {
        return null;
    }
}
