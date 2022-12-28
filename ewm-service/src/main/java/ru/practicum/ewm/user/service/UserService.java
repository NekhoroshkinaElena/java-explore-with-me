package ru.practicum.ewm.user.service;

import ru.practicum.ewm.user.dto.NewUserRequest;
import ru.practicum.ewm.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto save(NewUserRequest userDto);

    List<UserDto> getAll(List<Long> ids, int from, int size);

    void delete(long userId);
}
