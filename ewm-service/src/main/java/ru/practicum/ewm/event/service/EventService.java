package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.GetEventRequestForAdmin;
import ru.practicum.ewm.event.dto.GetEventRequestForAll;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.request.model.Request;

import java.util.List;

public interface EventService {

    Event save(NewEventDto newEventDto, long userId);

    Event publish(long eventId);

    Event rejectAdmin(long eventId);

    Event editAdmin(long eventId, NewEventDto newEventDto);

    Event editUser(long userId, EventShortDto eventShortDto);

    List<Event> getAllForUser(long userId);

    Event getByIdForUser(long userId, long eventId);

    Event rejectEventUser(long userId, long eventId);

    Request confirmRequestUser(long userId, long eventId, long reqId);

    Request rejectRequestUser(long userId, long eventId, long reqId);

    List<Request> getAllRequestForEventUser(long userId, long eventId);

    Event getEventById(long eventId, String uri, String ip);

    List<Event> searchEvents(GetEventRequestForAdmin requests);

    List<Event> getEventForAll(GetEventRequestForAll requests, String uri, String ip);
}
