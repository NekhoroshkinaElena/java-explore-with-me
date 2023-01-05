package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.comment.CommentDtoInput;
import ru.practicum.ewm.event.comment.CommentDtoOutput;
import ru.practicum.ewm.event.dto.EvenShortDtoForUser;
import ru.practicum.ewm.event.dto.EventOutputDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.request.dto.RequestDto;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
public class PrivateEventController {
    private final EventService eventService;

    @PostMapping
    public EventOutputDto addEvent(@Valid @RequestBody NewEventDto newEventDto,
                                   @PathVariable long userId) {
        log.info("Создание события авторизованным пользователем.");
        return eventService.save(newEventDto, userId);
    }

    @GetMapping("/{eventId}")
    public EventOutputDto getById(@PathVariable long userId, @PathVariable long eventId) {
        log.info("Получение полной информации о событии добавленном авторизованным пользователем.");
        return eventService.getByIdForUser(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventOutputDto rejectEventById(@PathVariable long userId,
                                          @PathVariable long eventId) {
        log.info("Отмена события добавленного авторизованным пользователем");
        return eventService.rejectUser(userId, eventId);
    }

    @GetMapping
    public List<EvenShortDtoForUser> getAllEventForUser(@PathVariable long userId,
                                                        @RequestParam(value = "from", required = false,
                                                                defaultValue = "0") int from,
                                                        @RequestParam(value = "size", required = false,
                                                                defaultValue = "10") int size
    ) {
        log.info("Получение событий, добавленных авторизованным пользователем.");
        return eventService.getAllForUser(userId, from, size);
    }


    @PatchMapping
    public EventOutputDto edit(@RequestBody EventShortDto eventShortDto,
                               @PathVariable long userId) {
        log.info("Редактирование события добавленного авторизованным пользователем.");
        return eventService.editUser(userId, eventShortDto);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public RequestDto confirmRequest(@PathVariable long userId,
                                     @PathVariable long eventId,
                                     @PathVariable long reqId) {
        log.info("Подтверждение авторизованным пользователем чужой заявки на участие в событии.");
        return eventService.confirmRequestUser(userId, eventId, reqId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public RequestDto rejectRequest(@PathVariable long userId,
                                    @PathVariable long eventId,
                                    @PathVariable long reqId) {
        log.info("Отклонение авторизованным пользователем чужой заявки на участие в событии.");
        return eventService.rejectRequestUser(userId, eventId, reqId);
    }

    @GetMapping("/{eventId}/requests")
    public List<RequestDto> getRequests(@PathVariable long userId,
                                        @PathVariable long eventId) {
        log.info("Получение информации о запросах на участие в событии авторизованного пользователя.");
        return eventService.getAllRequestForEvent(userId, eventId);
    }


    @PostMapping("/{eventId}/comments")
    public CommentDtoOutput addComment(@PathVariable long userId,
                                       @PathVariable long eventId,
                                       @Valid @RequestBody CommentDtoInput commentDtoInput) {
        log.info("Добавление комментария от авторизованного пользователя.");
        return eventService.addComment(userId, eventId, commentDtoInput);
    }

    @PatchMapping("/{eventId}/comments/{commentId}")
    public CommentDtoOutput editCommentUser(@PathVariable long userId,
                                            @PathVariable long eventId,
                                            @PathVariable long commentId,
                                            @Valid @RequestBody CommentDtoInput commentDtoInput) {
        log.info("Изменение комментария авторизованным пользователем");
        return eventService.editCommentUser(userId, eventId, commentId, commentDtoInput);
    }

    @DeleteMapping("/{eventId}/comments/{commentId}")
    public void deleteCommentUser(@PathVariable long userId,
                                  @PathVariable long eventId,
                                  @PathVariable long commentId) {
        log.info("Удаление комментария авторизованным пользователем");
        eventService.deleteCommentUser(userId, eventId, commentId);
    }
}
