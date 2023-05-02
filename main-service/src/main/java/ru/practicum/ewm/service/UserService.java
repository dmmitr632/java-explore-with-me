package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.NewUserDto;
import ru.practicum.ewm.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto addUser(NewUserDto newUserDto);

    String deleteUser(Integer userId);

    List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size);

}
