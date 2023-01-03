package ru.practicum.ewm.event.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.category.dto.CategoryDtoInput;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.event.dto.EvenShortDtoForUser;
import ru.practicum.ewm.event.dto.EventOutputDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.location.LocationDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.StateEvent;
import ru.practicum.ewm.user.dto.UserShortDto;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;

@UtilityClass
public class EventMapper {

    public static Event createEvent(NewEventDto newEventDto, User user, Category category) {
        return new Event(0L, newEventDto.getAnnotation(), category, 0, LocalDateTime.now(),
                newEventDto.getDescription(), TimeMapper.stringToTime(newEventDto.getEventDate()),
                user, newEventDto.getLocation().getLat(), newEventDto.getLocation().getLon(),
                newEventDto.getPaid(), newEventDto.getParticipantLimit(),
                null, newEventDto.getRequestModeration(), StateEvent.PENDING,
                newEventDto.getTitle(), 0);
    }

    public static EventOutputDto toEventDtoOutput(Event event) {
        return new EventOutputDto(event.getId(), event.getAnnotation(),
                CategoryMapper.toCategoryDtoOutput(event.getCategory()),
                event.getConfirmedRequest(), TimeMapper.timeToString(event.getCreatedOn()),
                event.getDescription(), TimeMapper.timeToString(event.getEventDate()), new UserShortDto(
                event.getInitiator().getId(), event.getInitiator().getName()), new LocationDto(event.getLat(),
                event.getLon()), event.getPaid(), event.getParticipantLimit(),
                event.getPublishedOn() != null ? TimeMapper.timeToString(event.getPublishedOn()) : "",
                event.getRequestModeration(), event.getState(), event.getTitle(), event.getViews());
    }

    public static EvenShortDtoForUser toEventDtoForUser(Event event) {
        return new EvenShortDtoForUser(event.getAnnotation(),
                new CategoryDtoInput(event.getCategory().getId(), event.getCategory().getName()),
                event.getConfirmedRequest(),
                TimeMapper.timeToString(event.getEventDate()), event.getId(), new UserShortDto(
                event.getInitiator().getId(), event.getInitiator().getName()),
                event.getPaid(), event.getTitle(), event.getViews());
    }
}
