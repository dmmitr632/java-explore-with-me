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
    public List<ParticipationRequestDto> getUserParticipationRequests(@PathVariable Integer userId) {
        return new ArrayList<>(participationRequestService.getUserParticipationRequests(userId));
    }

    @PostMapping(path = "/users/{userId}/requests")
    public ParticipationRequestDto addUserParticipationRequest(@PathVariable Integer userId, @RequestParam Integer eventId) {
        return participationRequestService.addUserParticipationRequest(userId, eventId);
    }


    @PatchMapping(path = "/users/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelUserParticipationRequest(@PathVariable Integer userId,
                                                                  @PathVariable Integer requestId) {
        return participationRequestService.cancelUserParticipationRequest(userId, requestId);
    }
}
