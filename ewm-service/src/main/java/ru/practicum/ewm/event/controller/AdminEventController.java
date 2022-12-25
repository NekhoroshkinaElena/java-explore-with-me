package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventOutputDto;
import ru.practicum.ewm.event.dto.GetEventRequestForAdmin;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.StateEvent;
import ru.practicum.ewm.event.service.EventService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/events")
public class AdminEventController {
    private final EventService eventService;

    @PatchMapping("/{eventId}/publish")
    public EventOutputDto publishEvent(@PathVariable long eventId) {
        return EventMapper.toEventDtoOutput(eventService.publish(eventId));
    }

    @PatchMapping("/{eventId}/reject")
    public EventOutputDto rejectEvent(@PathVariable long eventId) {
        return EventMapper.toEventDtoOutput(eventService.rejectAdmin(eventId));
    }

    @PutMapping("{eventId}")
    public EventOutputDto edit(@PathVariable long eventId, @RequestBody NewEventDto newEventDto) {
        return EventMapper.toEventDtoOutput(eventService.editAdmin(eventId, newEventDto));
    }

    @GetMapping
    public List<EventOutputDto> searchEvent(@RequestParam(value = "users", required = false) List<Long> users,
                                            @RequestParam(value = "states", required = false) List<StateEvent> states,
                                            @RequestParam(value = "categories", required = false) List<Long> categories,
                                            @RequestParam(value = "rangeStart", required = false) String rangeStart,
                                            @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
                                            @RequestParam(value = "from", required = false, defaultValue = "0")
                                            int from,
                                            @RequestParam(value = "size", required = false, defaultValue = "10")
                                            int size) {
        return eventService.searchEvents(GetEventRequestForAdmin.of(users, states, categories, rangeStart, rangeEnd, from, size))
                .stream().map(EventMapper::toEventDtoOutput).collect(Collectors.toList());
    }
}
