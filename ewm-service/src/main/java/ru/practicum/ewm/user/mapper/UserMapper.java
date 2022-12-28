package ru.practicum.ewm.user.mapper;

import ru.practicum.ewm.user.dto.NewUserRequest;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.model.User;

public class UserMapper {

    public static User toUser(NewUserRequest newUserRequest) {
        return new User(0L, newUserRequest.getName(), newUserRequest.getEmail());
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getEmail());
    }
}
