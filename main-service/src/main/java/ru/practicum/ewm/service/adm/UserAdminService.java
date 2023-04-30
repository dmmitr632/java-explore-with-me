package ru.practicum.ewm.service.adm;

import ru.practicum.ewm.dto.NewUserDto;
import ru.practicum.ewm.dto.UserDto;

import java.util.List;

public interface UserAdminService {

    UserDto getUsers(List<Integer> ids, Integer from, Integer size);

    UserDto addUser(NewUserDto newUserDto);

    void deleteUser(Long userId);
}
