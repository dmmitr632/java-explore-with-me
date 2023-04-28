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
    public List<UserRequestDto> getUserRequests(@RequestParam List<Integer> users,
                                                @RequestParam List<String> states,
                                                @RequestParam List<Integer> categories,
                                                @RequestParam String rangeStart,
                                                @RequestParam String rangeEnd,
                                                @RequestParam(defaultValue = "0") Integer from,
                                                @RequestParam(defaultValue = "10") Integer size) {
        return userRequestPrivateService.getUserRequests(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PostMapping(path = "/users/{userId}/requests")
    public List<UserRequestDto> addUserRequest(@RequestParam Integer eventId,
                                                @RequestBody UserRequestDto eventDto) {
        return userRequestPrivateService.addUserRequest(eventId, eventDto);
    }


    @PatchMapping(path = "/users/{userId}/requests/{requestId}/cancel")
    public List<UserRequestDto>cancelUserRequest(@RequestParam Integer eventId,
                                    @RequestBody UserRequestDto eventDto) {
        return userRequestPrivateService.cancelUserRequest(eventId, eventDto);
    }
}
