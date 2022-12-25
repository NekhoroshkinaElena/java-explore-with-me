package ru.practicum.ewm.request.service;

import ru.practicum.ewm.request.model.Request;

import java.util.List;

public interface RequestService {

    Request save(long userId, long eventId);

    Request cancel(long userId, long requestId);

    List<Request> getAllRequestUser(long userId);
}
