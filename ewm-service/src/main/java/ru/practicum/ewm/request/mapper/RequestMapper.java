package ru.practicum.ewm.request.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.event.mapper.TimeMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.request.dto.RequestDto;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestStatus;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;

@UtilityClass
public class RequestMapper {

    public static RequestDto toRequestDto(Request request) {
        return new RequestDto(request.getId(), request.getRequester().getId(), request.getEvent().getId(),
                request.getStatus(), request.getCreated());
    }

    public static Request createRequest(User user, Event event) {
        return new Request(0L, TimeMapper.timeToString(LocalDateTime.now()),
                event, user, RequestStatus.PENDING);
    }
}
