package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.request.dto.RequestDto;

import java.util.List;

public interface EventService {

    EventOutputDto save(NewEventDto eventDto, long userId);

    EventOutputDto publish(long eventId);

    EventOutputDto rejectAdmin(long eventId);

    EventOutputDto editAdmin(long eventId, EventDtoForEditAdmin newEventDto);

    EventOutputDto editUser(long userId, EventShortDto eventShortDto);

    List<EvenShortDtoForUser> getAllForUser(long userId, int from, int size);

    EventOutputDto getByIdForUser(long userId, long eventId);

    EventOutputDto rejectUser(long userId, long eventId);

    RequestDto confirmRequestUser(long userId, long eventId, long reqId);

    RequestDto rejectRequestUser(long userId, long eventId, long reqId);

    List<RequestDto> getAllRequestForEvent(long userId, long eventId);

    EventOutputDto getEventById(long eventId);

    List<EventOutputDto> searchEventsAdmin(GetEventRequestForAdmin requests);

    List<EventOutputDto> getEventsForAll(GetEventRequestForAll requests);
}
