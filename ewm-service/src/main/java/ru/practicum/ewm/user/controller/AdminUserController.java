package ru.practicum.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.dto.NewUserRequest;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class AdminUserController {
    private final UserService userService;

    @PostMapping("/users")
    public UserDto post(@Valid @RequestBody NewUserRequest user) {
        log.info("Добавление нового пользователя.");
        return userService.save(user);
    }

    @GetMapping("/users")
    public List<UserDto> getAll(@RequestParam(value = "ids", required = false) List<Long> ids,
                                @RequestParam(value = "from", required = false, defaultValue = "0") int from,
                                @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        log.info("Получение информации либо о всех пользователях, либо по id.");
        return userService.getAll(ids, from, size);
    }

    @DeleteMapping("/users/{userId}")
    public void delete(@PathVariable long userId) {
        log.info("Удаление пользователя.");
        userService.delete(userId);
    }
}
