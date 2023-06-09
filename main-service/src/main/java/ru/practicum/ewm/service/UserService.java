package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.NewUserRequestDto;
import ru.practicum.ewm.dto.UserDto;

import java.util.Collection;
import java.util.List;

public interface UserService {

    UserDto addUser(NewUserRequestDto newUserRequestDto);

    void deleteUser(Integer userId);

    Collection<UserDto> getUsers(List<Integer> ids, Integer from, Integer size);

}
