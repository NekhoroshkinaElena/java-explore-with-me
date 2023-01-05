package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.comment.CommentDtoOutput;
import ru.practicum.ewm.event.dto.EventDtoForEditAdmin;
import ru.practicum.ewm.event.dto.EventOutputDto;
import ru.practicum.ewm.event.dto.GetEventRequestForAdmin;
import ru.practicum.ewm.event.model.StateEvent;
import ru.practicum.ewm.event.service.EventService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("admin/events")
public class AdminEventController {
    private final EventService eventService;

    @PatchMapping("/{eventId}/publish")
    public EventOutputDto publish(@PathVariable long eventId) {
        log.info("Публикация события администратором.");
        return eventService.publish(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventOutputDto reject(@PathVariable long eventId) {
        log.info("Отклонение события администратором.");
        return eventService.rejectAdmin(eventId);
    }

    @PutMapping("{eventId}")
    public EventOutputDto edit(@PathVariable long eventId, @RequestBody EventDtoForEditAdmin newEventDto) {
        log.info("Редактирование события администратором администратором.");
        return eventService.editAdmin(eventId, newEventDto);
    }

    @GetMapping
    public List<EventOutputDto> search(@RequestParam(value = "users", required = false) List<Long> users,
                                       @RequestParam(value = "states", required = false) List<StateEvent> states,
                                       @RequestParam(value = "categories", required = false) List<Long> categories,
                                       @RequestParam(value = "rangeStart", required = false) String rangeStart,
                                       @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
                                       @RequestParam(value = "from", required = false, defaultValue = "0")
                                       int from,
                                       @RequestParam(value = "size", required = false, defaultValue = "10")
                                       int size) {
        log.info("Поиск событий администратором.");
        return eventService.searchEventsAdmin(GetEventRequestForAdmin.of(users, states, categories, rangeStart,
                rangeEnd, from, size));
    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteCommentAdmin(@PathVariable long commentId) {
        log.info("Удаление комментария администратором за нарушение правил.");
        eventService.deleteCommentAdmin(commentId);
    }

    @GetMapping("/comments")
    public List<CommentDtoOutput> searchComments(@RequestParam(value = "text", required = false, defaultValue = "")
                                                 String text) {
        log.info("Поиск комментариев по тексту.");
        return eventService.searchComments(text);
    }
}
