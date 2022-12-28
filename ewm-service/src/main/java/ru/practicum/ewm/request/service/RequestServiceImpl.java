package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.StateEvent;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ValidationException;
import ru.practicum.ewm.request.dto.RequestDto;
import ru.practicum.ewm.request.mapper.RequestMapper;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public RequestDto save(long userId, long eventId) {
        if (requestRepository.findRequestByEventIdAndRequesterId(eventId, userId) != null) {
            log.error("Нельзя добавить повторный запрос.");
            throw new ValidationException("Нельзя добавить повторный запрос.");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("Пользователь с id " + userId + " не найден.");
            throw new NotFoundException("Пользователь с id " + userId + " не найден.");
        });
        Event event = eventRepository.findById(eventId).orElseThrow(() -> {
                    log.error("Событие с id " + eventId + " не найдено.");
                    throw new NotFoundException("Событие с id " + eventId + " не найдено.");
                }
        );
        if (event.getInitiator().getId() == userId) {
            log.error("Инициатор события не может добавить запрос на участие в своём событии.");
            throw new ValidationException("Инициатор события не может добавить запрос на участие в своём событии.");
        }
        if (!event.getState().equals(StateEvent.PUBLISHED)) {
            log.error("Нельзя участвовать в неопубликованном событии.");
            throw new ValidationException("Нельзя участвовать в неопубликованном событии.");
        }
        if (event.getParticipantLimit() != 0 && requestRepository
                .findAllByEventId(eventId).size() >= event.getParticipantLimit()) {
            log.error("У событи достигнут лимит запросов на участие.");
            throw new ValidationException("У событи достигнут лимит запросов на участие.");
        }
        Request request = RequestMapper.toRequest(user, event);
        if (!event.getRequestModeration()) {
            request.setStatus("CONFIRMED");
        }
        return RequestMapper.toRequestDto(requestRepository.save(request));
    }

    @Override
    public RequestDto cancel(long userId, long requestId) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> {
            log.error("Заявка с id" + requestId + " не найдена.");
            throw new NotFoundException("Заявка с id" + requestId + " не найдена.");
        });
        if (request.getRequester().getId() == userId) {
            request.setStatus("CANCELED");
            return RequestMapper.toRequestDto(request);
        } else {
            throw new NotFoundException("Вы не можете отменить заявку.");
        }
    }

    @Override
    public List<RequestDto> getAllRequestUser(long userId) {
        return requestRepository.findAllByRequesterId(userId).stream()
                .map(RequestMapper::toRequestDto).collect(Collectors.toList());
    }
}
