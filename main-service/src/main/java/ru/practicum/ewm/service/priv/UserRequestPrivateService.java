package ru.practicum.ewm.service.priv;

import ru.practicum.ewm.dto.UserRequestDto;

import java.util.List;

public interface UserRequestPrivateService {
    List<UserRequestDto> getUserRequests(List<Integer> users, List<String> states, List<Integer> categories, String rangeStart, String rangeEnd, Integer from, Integer size);

    List<UserRequestDto> addUserRequest(Integer eventId, UserRequestDto eventDto);

    List<UserRequestDto> cancelUserRequest(Integer eventId, UserRequestDto eventDto);
}
