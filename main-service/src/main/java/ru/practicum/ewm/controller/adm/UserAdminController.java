package ru.practicum.ewm.controller.adm;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.UserDto;
import ru.practicum.ewm.service.adm.UserAdminService;

import java.util.List;

public class UserAdminController {
    private final UserAdminService userAdminService;

    public UserAdminController(UserAdminService userAdminService) {
        this.userAdminService = userAdminService;
    }

    @GetMapping(path = "/admin/users/{userId}")
    public List<UserDto> getUsers(@RequestParam List<Integer> users,
                                  @RequestParam List<String> states,
                                  @RequestParam List<Integer> categories,
                                  @RequestParam String rangeStart,
                                  @RequestParam String rangeEnd,
                                  @RequestParam(defaultValue = "0") Integer from,
                                  @RequestParam(defaultValue = "10") Integer size) {
        return userAdminService.getUsers(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PostMapping(path = "/admin/users/{userId}")
    public UserDto addUser(@RequestParam Integer userId,
                           @RequestBody UserDto userDto) {
        return userAdminService.editUser(userId, userDto);
    }

    @DeleteMapping(path = "/admin/users/{userId}")
    public void deleteUser(@RequestParam Integer userId,
                           @RequestBody UserDto userDto) {
    }

}
