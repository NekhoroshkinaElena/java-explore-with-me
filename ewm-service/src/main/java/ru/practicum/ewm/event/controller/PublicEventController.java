package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventOutputDto;
import ru.practicum.ewm.event.dto.GetEventRequestForAll;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/events")
@RequiredArgsConstructor
@RestController
public class PublicEventController {
    private final EventService eventService;

    @GetMapping("/{id}")
    public EventOutputDto getEventById(@PathVariable long id, HttpServletRequest request) {
        log.info("client ip: {}", request.getRemoteAddr());
        log.info("endpoint path: {}", request.getRequestURI());
        return EventMapper.toEventDtoOutput(
                eventService.getEventById(id, request.getRequestURI(), request.getRemoteAddr()));
    }

    @GetMapping
    public List<EventOutputDto> getEvents(HttpServletRequest request,
                                          @RequestParam(value = "text", required = false) String text,
                                          @RequestParam(value = "categories", required = false) List<Long> categories,
                                          @RequestParam(value = "paid", required = false) Boolean paid,
                                          @RequestParam(value = "rangeStart", required = false) String rangeStart,
                                          @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
                                          @RequestParam(value = "onlyAvailable",
                                                  required = false, defaultValue = "false") Boolean onlyAvailable,
                                          @RequestParam(value = "sort", required = false) Sort sort,
                                          @RequestParam(value = "from", required = false, defaultValue = "0")
                                          int from,
                                          @RequestParam(value = "size", required = false, defaultValue = "10")
                                          int size) {
        return eventService.getEventForAll(GetEventRequestForAll.of(
                                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size),
                        request.getRequestURI(), request.getRemoteAddr())
                .stream().map(EventMapper::toEventDtoOutput).collect(Collectors.toList());
    }
}
