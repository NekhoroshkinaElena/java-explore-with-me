package ru.practicum.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.model.UserShortDto;
import ru.practicum.ewm.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;

    @PostMapping("/users")
    public User post(@Valid @RequestBody UserShortDto user) {
        return userService.save(user);
    }

    @GetMapping("/users")
    public List<User> getAll(@RequestParam(value = "ids") List<Long> ids,
                             @RequestParam(value = "from", required = false, defaultValue = "0") int from,
                             @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        return userService.getAllById(ids);
    }

    @DeleteMapping("/users/{userId}")
    public void delete(@PathVariable long userId) {
        userService.delete(userId);
    }
}
