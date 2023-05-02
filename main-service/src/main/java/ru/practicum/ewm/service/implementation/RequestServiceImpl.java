package ru.practicum.ewm.service.implementation;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.UserRequestDto;
import ru.practicum.ewm.service.RequestService;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
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
