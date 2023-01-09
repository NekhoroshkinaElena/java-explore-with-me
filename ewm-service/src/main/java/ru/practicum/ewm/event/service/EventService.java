package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.comment.CommentDtoInput;
import ru.practicum.ewm.event.comment.CommentDtoOutput;
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

    EventOutputDtoWithComments getEventById(long eventId);

    List<EventOutputDto> searchEventsAdmin(GetEventRequestForAdmin requests);

    List<EventOutputDto> getEventsForAll(GetEventRequestForAll requests);

    CommentDtoOutput addComment(long userId, long eventId, CommentDtoInput commentDtoInput);

    CommentDtoOutput editCommentUser(long userId, long eventId, long commentId, CommentDtoInput commentDtoInput);

    void deleteCommentUser(long userId, long eventId, long commentId);

    void deleteCommentAdmin(long commentId);

    List<CommentDtoOutput> searchComments(String text);
}
