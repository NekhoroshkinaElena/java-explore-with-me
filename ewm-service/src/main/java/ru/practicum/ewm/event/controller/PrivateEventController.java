package ru.practicum.ewm.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventOutputDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.service.EventService;
import ru.practicum.ewm.request.model.RequestDto;
import ru.practicum.ewm.request.model.RequestMapper;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventController {
    private final EventService eventService;

    @PostMapping
    public EventOutputDto addEvent(@Valid @RequestBody NewEventDto newEventDto,
                                   @PathVariable long userId) {
        return EventMapper.toEventDtoOutput(eventService.save(newEventDto, userId));
    }

    @GetMapping("/{eventId}")
    public EventOutputDto getById(@PathVariable long userId, @PathVariable long eventId) {
        return EventMapper.toEventDtoOutput(eventService.getByIdForUser(userId, eventId));
    }

    @PatchMapping("/{eventId}")
    public EventOutputDto rejectEventById(@PathVariable long userId,
                                          @PathVariable long eventId) {
        return EventMapper.toEventDtoOutput(eventService.rejectEventUser(userId, eventId));
    }

    @GetMapping
    public List<EventOutputDto> getAllEventForUser(@PathVariable long userId,
                                                   @RequestParam(value = "from", required = false, defaultValue = "0") int from,
                                                   @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        return eventService.getAllForUser(userId).stream()
                .map(EventMapper::toEventDtoOutput).collect(Collectors.toList());
    }


    @PatchMapping
    public EventOutputDto edit(@RequestBody EventShortDto eventShortDto,
                               @PathVariable long userId) {
        return EventMapper.toEventDtoOutput(eventService.editUser(userId, eventShortDto));
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    public RequestDto confirmRequest(@PathVariable long userId,
                                     @PathVariable long eventId,
                                     @PathVariable long reqId) {
        return RequestMapper.toRequestDto(eventService.confirmRequestUser(userId, eventId, reqId));
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    public RequestDto rejectRequest(@PathVariable long userId,
                                    @PathVariable long eventId,
                                    @PathVariable long reqId) {
        return RequestMapper.toRequestDto(eventService.rejectRequestUser(userId, eventId, reqId));
    }

    @GetMapping("/{eventId}/requests")
    public List<RequestDto> getRequests(@PathVariable long userId, @PathVariable long eventId) {
        return eventService.getAllRequestForEventUser(userId, eventId).stream()
                .map(RequestMapper::toRequestDto).collect(Collectors.toList());
    }
}
