package ru.practicum.ewm.service.adm;

import ru.practicum.ewm.dto.UserDto;

import java.util.List;

public interface UserAdminService {
    List<UserDto> getUsers(List<Integer> users, List<String> states, List<Integer> categories, String rangeStart, String rangeEnd, Integer from, Integer size);

    UserDto editUser(Integer userId, UserDto userDto);
}
