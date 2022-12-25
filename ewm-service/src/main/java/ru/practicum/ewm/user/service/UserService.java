package ru.practicum.ewm.user.service;

import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.model.UserShortDto;

import java.util.List;

public interface UserService {

    User save(UserShortDto userDto);

    List<User> getAllById(List<Long> ids);

    void delete(long userId);
}
