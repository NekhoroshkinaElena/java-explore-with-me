package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.client.EventClient;
import ru.practicum.ewm.event.dto.EventOutputDto;
import ru.practicum.ewm.event.dto.GetEventRequestForAll;
import ru.practicum.ewm.event.model.Sort;
import ru.practicum.ewm.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RequestMapping("/events")
@RequiredArgsConstructor
@RestController
public class PublicEventController {
    private final EventService eventService;
    private final EventClient eventClient;

    @GetMapping("/{id}")
    public EventOutputDto getById(@PathVariable long id, HttpServletRequest request) {
        log.info("Получение информации о событии.");
        eventClient.saveRequestInStatistic(request.getRequestURI(), request.getRemoteAddr());
        return eventService.getEventById(id);
    }

    @GetMapping
    public List<EventOutputDto> getAll(HttpServletRequest request,
                                       @RequestParam(value = "text", required = false) String text,
                                       @RequestParam(value = "categories", required = false) List<Long> categories,
                                       @RequestParam(value = "paid", required = false) Boolean paid,
                                       @RequestParam(value = "rangeStart", required = false) String rangeStart,
                                       @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
                                       @RequestParam(value = "onlyAvailable", required = false,
                                               defaultValue = "false") Boolean onlyAvailable,
                                       @RequestParam(value = "sort", required = false) Sort sort,
                                       @RequestParam(value = "from", required = false, defaultValue = "0") int from,
                                       @RequestParam(value = "size", required = false, defaultValue = "10")
                                       int size) {
        log.info("получение информации о событиях.");
        eventClient.saveRequestInStatistic(request.getRequestURI(), request.getRemoteAddr());
        return eventService.getEventsForAll(GetEventRequestForAll.of(
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size));
    }
}
