package ru.practicum.ewm.controller.priv;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.UserRequestDto;
import ru.practicum.ewm.service.RequestService;

import java.util.List;

public class RequestPrivateController {
    private final RequestService requestService;

    public RequestPrivateController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping(path = "/users/{userId}/requests")
    public List<UserRequestDto> getUserRequests(@PathVariable Integer userId) {
        return requestService.getUserRequests(userId);
    }

    @PostMapping(path = "/users/{userId}/requests")
    public UserRequestDto addUserRequest(@RequestParam Integer eventId,
                                         @RequestBody UserRequestDto eventDto) {
        return requestService.addUserRequest(eventId, eventDto);
    }


    @PatchMapping(path = "/users/{userId}/requests/{requestId}/cancel")
    public UserRequestDto cancelUserRequest(@PathVariable Integer requestId,
                                            @PathVariable Integer userId) {
        return requestService.cancelUserRequest(requestId, userId);
    }
}
