package ru.practicum.ewm.controller.adm;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.NewUserDto;
import ru.practicum.ewm.dto.UserDto;
import ru.practicum.ewm.service.adm.UserAdminService;

import javax.validation.Valid;
import java.util.List;

public class UserAdminController {
    private final UserAdminService userAdminService;

    public UserAdminController(UserAdminService userAdminService) {
        this.userAdminService = userAdminService;
    }

    @GetMapping
    public UserDto getUsers(@RequestParam(required = false, name = "ids") List<Integer> ids,
                           @RequestParam(name = "from", defaultValue = "0") Integer from,
                            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return userAdminService.getUsers(ids, from, size);
    }

    @PostMapping("/admin/users")
    public UserDto addUser(@RequestBody @Valid NewUserDto newUserDto) {
        return userAdminService.addUser(newUserDto);
    }

    @DeleteMapping("/admin/users{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userAdminService.deleteUser(userId);
    }



}
