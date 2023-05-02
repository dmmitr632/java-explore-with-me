package ru.practicum.ewm.service.implementation;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.ParticipationRequestDto;
import ru.practicum.ewm.service.RequestService;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    @Override
    public List<ParticipationRequestDto> getUserRequests(Integer userId) {
        return null;
    }

    @Override
    public ParticipationRequestDto addUserRequest(Integer userId, Integer eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto cancelUserRequest(Integer userId, Integer requestId) {
        return null;
    }
}
