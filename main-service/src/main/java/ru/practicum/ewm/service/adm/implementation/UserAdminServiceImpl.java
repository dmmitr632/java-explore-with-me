package ru.practicum.ewm.service.adm.implementation;

import ru.practicum.ewm.dto.NewUserDto;
import ru.practicum.ewm.dto.UserDto;
import ru.practicum.ewm.service.adm.UserAdminService;

import java.util.List;

public class UserAdminServiceImpl implements UserAdminService {
    @Override
    public UserDto getUsers(List<Integer> ids, Integer from, Integer size) {
        return null;
    }

    @Override
    public UserDto addUser(NewUserDto newUserDto) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }
}
