package ru.practicum.ewm.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.request.model.RequestDto;
import ru.practicum.ewm.request.model.RequestMapper;
import ru.practicum.ewm.request.service.RequestService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class PrivateRequestController {
    private final RequestService requestService;

    @PostMapping
    public RequestDto add(@PathVariable long userId, @RequestParam long eventId) {
        return RequestMapper.toRequestDto(requestService.save(userId, eventId));
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancel(@PathVariable long userId, @PathVariable long requestId) {
        return RequestMapper.toRequestDto(requestService.cancel(userId, requestId));
    }

    @GetMapping
    public List<RequestDto> getAllRequestForEven(@PathVariable long userId) {
        return requestService.getAllRequestUser(userId).stream()
                .map(RequestMapper::toRequestDto).collect(Collectors.toList());
    }
}
