package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.exception.AlreadyExistsException;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.model.UserShortDto;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public User save(UserShortDto userDto) {
        if (userRepository.findUserByName(userDto.getName()) != null) {
            log.error("пользователь с таким именем уже существует");
            throw new AlreadyExistsException("пользователь с таким именем уже существует");
        }
        User user = new User(0, userDto.getName(), userDto.getEmail());
        return userRepository.save(user);
    }

    public List<User> getAllById(List<Long> ids) {
        return userRepository.findAll().stream().filter(user ->
                ids.contains(user.getId())).collect(Collectors.toList());//добавить пагинацию
    }

    public void delete(long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        userRepository.delete(user);
    }
}
