package ru.practicum.ewm.service.adm.implementation;

import ru.practicum.ewm.dto.UserDto;
import ru.practicum.ewm.service.adm.UserAdminService;

import java.util.List;

public class UserAdminServiceImpl implements UserAdminService {
    @Override
    public List<UserDto> getUsers(List<Integer> users, List<String> states, List<Integer> categories, String rangeStart, String rangeEnd, Integer from, Integer size) {
        return null;
    }

    @Override
    public UserDto editUser(Integer userId, UserDto userDto) {
        return null;
    }
}
