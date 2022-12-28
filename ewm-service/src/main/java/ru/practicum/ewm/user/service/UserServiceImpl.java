package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.AlreadyExistsException;
import ru.practicum.ewm.exception.NotFoundException;
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
        User user = UserMapper.toUser(newUserRequest);
        return UserMapper.toUserDto(userRepository.save(user));
    }

    public List<UserDto> getAllById(List<Long> ids, int from, int size) {
        if (ids != null) {
            return userRepository.findAll().stream().filter(user ->
                    ids.contains(user.getId())).map(UserMapper::toUserDto).collect(Collectors.toList());
        } else {
            return userRepository.findAll(PageRequest.of(from, size))
                    .stream().map(UserMapper::toUserDto).collect(Collectors.toList());
        }
    }

    public void delete(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("Пользователь с id " + userId + "не существует.");
            throw new NotFoundException("Пользователь с id " + userId + "не существует.");
        });
        userRepository.delete(user);
    }
}
