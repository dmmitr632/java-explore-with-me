package ru.practicum.ewm.controller.priv;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.ParticipationRequestDto;
import ru.practicum.ewm.service.RequestService;

import java.util.List;

public class RequestPrivateController {
    private final RequestService requestService;

    public RequestPrivateController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping(path = "/users/{userId}/requests")
    public List<ParticipationRequestDto> getUserRequests(@PathVariable Integer userId) {
        return requestService.getUserRequests(userId);
    }

    @PostMapping(path = "/users/{userId}/requests")
    public ParticipationRequestDto addUserRequest(@PathVariable Integer userId, @RequestParam Integer eventId) {
        return requestService.addUserRequest(userId, eventId);
    }


    @PatchMapping(path = "/users/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelUserRequest(@PathVariable Integer userId,
                                                     @PathVariable Integer requestId) {
        return requestService.cancelUserRequest(userId, requestId);
    }
}
