package ru.practicum.ewm.mapper;

import ru.practicum.ewm.dto.NewUserDto;
import ru.practicum.ewm.dto.UserDto;
import ru.practicum.ewm.dto.UserShortDto;
import ru.practicum.ewm.model.User;

public class UserMapper {


    public static User toUser(NewUserDto newUserDto) {
        return new User(newUserDto.getEmail(), newUserDto.getName());
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(user.getEmail(), user.getId(), user.getName());
    }

    public static UserShortDto toUserShortDto(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }

}
