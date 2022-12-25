package ru.practicum.ewm.request.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RequestMapper {

    public static RequestDto toRequestDto(Request request) {
        return new RequestDto(request.getId(), request.getRequester().getId(), request.getEvent().getId(),
                request.getStatus(), request.getCreated());
    }
}
