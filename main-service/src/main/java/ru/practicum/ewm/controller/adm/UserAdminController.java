package ru.practicum.ewm.controller.adm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.NewUserRequestDto;
import ru.practicum.ewm.dto.UserDto;
import ru.practicum.ewm.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class UserAdminController {
    private final UserService userService;

    public UserAdminController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/admin/users")
    public List<UserDto> getUsers(@RequestParam(required = false, name = "ids") List<Integer> ids,
                                  @RequestParam(name = "from", defaultValue = "0") Integer from,
                                  @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("                                                                           ");
        log.info("========================================");
        log.info("Получение списка пользователей администратором, ids {}, from {}, size {}", ids, from, size);
        log.info("========================================");
        log.info("                                                                           ");
        return new ArrayList<>(userService.getUsers(ids, from, size));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admin/users")
    public UserDto addUser(@RequestBody @Valid NewUserRequestDto newUserRequestDto) {
        log.info("                                                                           ");
        log.info("========================================");
        log.info("Добавление пользователя администратором, newUserRequestDto {}", newUserRequestDto);
        log.info("========================================");
        log.info("                                                                           ");
        return userService.addUser(newUserRequestDto);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/admin/users/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        log.info("                                                                           ");
        log.info("========================================");
        log.info("Удаление пользователя администратором, userId {}", userId);
        log.info("========================================");
        log.info("                                                                           ");
        userService.deleteUser(userId);
    }


}
