package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.AlreadyExistsException;
import ru.practicum.ewm.user.dto.NewUserRequest;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.mapper.UserMapper;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserDto save(NewUserRequest newUserRequest) {
        if (userRepository.findUserByName(newUserRequest.getName()) != null) {
            log.error("Пользователь с таким именем уже существует.");
            throw new AlreadyExistsException("Пользователь с таким именем уже существует.");
        }
        User user = UserMapper.createUser(newUserRequest);
        return UserMapper.toUserDto(userRepository.save(user));
    }

    public List<UserDto> getAll(List<Long> ids, int from, int size) {
        if (ids == null || ids.isEmpty()) {
            return userRepository.findAll(PageRequest.of(from, size))
                    .stream().map(UserMapper::toUserDto).collect(Collectors.toList());
        } else {
            return userRepository.findAllById(ids).stream().map(UserMapper::toUserDto).collect(Collectors.toList());
        }
    }

    public void delete(long userId) {
        userRepository.deleteById(userId);
    }
}
