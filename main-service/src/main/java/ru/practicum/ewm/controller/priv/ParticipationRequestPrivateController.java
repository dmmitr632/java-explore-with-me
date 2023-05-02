package ru.practicum.ewm.controller.priv;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.ParticipationRequestDto;
import ru.practicum.ewm.service.ParticipationRequestService;

import java.util.ArrayList;
import java.util.List;

public class ParticipationRequestPrivateController {
    private final ParticipationRequestService participationRequestService;

    public ParticipationRequestPrivateController(ParticipationRequestService participationRequestService) {
        this.participationRequestService = participationRequestService;
    }

    @GetMapping(path = "/users/{userId}/requests")
    public List<ParticipationRequestDto> getUserRequests(@PathVariable Integer userId) {
        return new ArrayList<>(participationRequestService.getUserRequests(userId));
    }

    @PostMapping(path = "/users/{userId}/requests")
    public ParticipationRequestDto addUserRequest(@PathVariable Integer userId, @RequestParam Integer eventId) {
        return participationRequestService.addUserRequest(userId, eventId);
    }


    @PatchMapping(path = "/users/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelUserRequest(@PathVariable Integer userId,
                                                     @PathVariable Integer requestId) {
        return participationRequestService.cancelUserRequest(userId, requestId);
    }
}
