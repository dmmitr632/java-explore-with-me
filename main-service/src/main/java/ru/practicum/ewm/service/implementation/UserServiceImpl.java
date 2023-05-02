package ru.practicum.ewm.service.implementation;

import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.NewUserRequestDto;
import ru.practicum.ewm.dto.UserDto;
import ru.practicum.ewm.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserDto addUser(NewUserRequestDto newUserRequestDto) {
        return null;
    }

    @Override
    public void deleteUser(Integer userId) {

    }

    @Override
    public List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size) {
        return null;
    }
}
