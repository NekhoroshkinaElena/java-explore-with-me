package ru.practicum.ewm.event.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.model.CategoryDtoInput;
import ru.practicum.ewm.event.dto.EvenShortDtoForUser;
import ru.practicum.ewm.event.dto.EventOutputDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.StateEvent;
import ru.practicum.ewm.user.model.UserOutPutDto;

import java.time.LocalDateTime;

@UtilityClass
public class EventMapper {

    public static Event toEvent(NewEventDto newEventDto) {
        return new Event(0L, newEventDto.getAnnotation(), new Category(), 0,
                LocalDateTime.now(), newEventDto.getDescription(),
                TimeMapper.stringToTime(newEventDto.getEventDate()),
                null, newEventDto.getLocation(), newEventDto.getPaid(), newEventDto.getParticipantLimit(),
                null,
                newEventDto.getRequestModeration(),
                StateEvent.PENDING, newEventDto.getTitle(), 0);
    }

    public static EventOutputDto toEventDtoOutput(Event event) {
        return new EventOutputDto(event.getId(), event.getAnnotation(), event.getCategory(),
                event.getConfirmedRequest(), TimeMapper.timeToString(event.getCreatedOn()),
                event.getDescription(), TimeMapper.timeToString(event.getEventDate()), new UserOutPutDto(
                event.getInitiator().getId(), event.getInitiator().getName()), event.getLocation(),
                event.getPaid(), event.getParticipantLimit(),
                TimeMapper.timeToString(LocalDateTime.now()), event.getRequestModeration(),
                event.getState(), event.getTitle(), event.getViews());
    }

    public static EvenShortDtoForUser toEventDtoForUser(Event event) {
        return new EvenShortDtoForUser(event.getAnnotation(),
                new CategoryDtoInput(event.getCategory().getId(), event.getCategory().getName()),
                event.getConfirmedRequest(),
                TimeMapper.timeToString(event.getEventDate()), event.getId(), new UserOutPutDto(
                event.getInitiator().getId(), event.getInitiator().getName()),
                event.getPaid(), event.getTitle(), event.getViews());
    }
}
