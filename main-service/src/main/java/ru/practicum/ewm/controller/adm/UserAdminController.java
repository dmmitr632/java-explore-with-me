package ru.practicum.ewm.controller.adm;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.NewUserDto;
import ru.practicum.ewm.dto.UserDto;
import ru.practicum.ewm.service.UserService;

import javax.validation.Valid;
import java.util.List;

public class UserAdminController {
    private final UserService userService;

    public UserAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UserDto getUsers(@RequestParam(required = false, name = "ids") List<Integer> ids,
                            @RequestParam(name = "from", defaultValue = "0") Integer from,
                            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return userService.getUsers(ids, from, size);
    }

    @PostMapping("/admin/users")
    public UserDto addUser(@RequestBody @Valid NewUserDto newUserDto) {
        return userService.addUser(newUserDto);
    }

    @DeleteMapping("/admin/users{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
    }


}
