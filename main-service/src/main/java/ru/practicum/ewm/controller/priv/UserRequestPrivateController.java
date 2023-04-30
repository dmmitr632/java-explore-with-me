package ru.practicum.ewm.controller.priv;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.UserRequestDto;
import ru.practicum.ewm.service.priv.UserRequestPrivateService;

import java.util.List;

public class UserRequestPrivateController {
    private final UserRequestPrivateService userRequestPrivateService;

    public UserRequestPrivateController(UserRequestPrivateService userRequestPrivateService) {
        this.userRequestPrivateService = userRequestPrivateService;
    }

    @GetMapping(path = "/users/{userId}/requests")
    public List<UserRequestDto> getUserRequests(@PathVariable Integer userId) {
        return userRequestPrivateService.getUserRequests(userId);
    }

    @PostMapping(path = "/users/{userId}/requests")
    public UserRequestDto addUserRequest(@RequestParam Integer eventId,
                                               @RequestBody UserRequestDto eventDto) {
        return userRequestPrivateService.addUserRequest(eventId, eventDto);
    }


    @PatchMapping(path = "/users/{userId}/requests/{requestId}/cancel")
    public UserRequestDto cancelUserRequest(@PathVariable Integer requestId,
                                            @PathVariable Integer userId) {
        return userRequestPrivateService.cancelUserRequest(requestId, userId);
    }
}
