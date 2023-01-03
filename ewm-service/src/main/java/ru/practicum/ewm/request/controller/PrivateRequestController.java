package ru.practicum.ewm.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.request.dto.RequestDto;
import ru.practicum.ewm.request.service.RequestService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class PrivateRequestController {
    private final RequestService requestService;

    @PostMapping
    public RequestDto add(@PathVariable long userId, @RequestParam long eventId) {
        log.info("Добавление запроса от авторизованного пользователя на участие в событии.");
        return requestService.save(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancel(@PathVariable long userId, @PathVariable long requestId) {
        log.info("Отмена запроса от авторизованного пользователя на участие в событии.");
        return requestService.cancel(userId, requestId);
    }

    @GetMapping
    public List<RequestDto> getAllRequestForEven(@PathVariable long userId) {
        log.info("Получение информации о заявках авторизованного пользователя на участие в чужих событиях.");
        return requestService.getAllRequestUser(userId);
    }
}
