package ru.practicum.ewm.service.implementation;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.dto.NewUserRequestDto;
import ru.practicum.ewm.dto.UserDto;
import ru.practicum.ewm.exception.BadRequestException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.mapper.UserMapper;
import ru.practicum.ewm.model.User;
import ru.practicum.ewm.repository.UserRepository;
import ru.practicum.ewm.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDto addUser(NewUserRequestDto newUserRequestDto) {
        if (newUserRequestDto.getName() == null) {
            throw new BadRequestException("У пользователя нет имени");
        }

        User user = userRepository.save(UserMapper.toUserFromNewUserRequestDto(newUserRequestDto));
        return UserMapper.toUserDto(user);
    }

    @Override
    @Transactional
    public void deleteUser(Integer userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new NotFoundException("Пользователь не найден");
        }
    }

    @Override
    @Transactional
    public List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size) {
        Pageable pageRequest = PageRequest.of(from, size);
        if (ids.isEmpty()) {
            return userRepository.findAll(pageRequest)
                    .stream()
                    .map(UserMapper::toUserDto)
                    .collect(Collectors.toList());
        }
        return userRepository.findAllByIdInOrderByIdAsc(ids, pageRequest)
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }
}

