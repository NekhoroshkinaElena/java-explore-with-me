package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.mapper.TimeMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public Request save(long userId, long eventId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("пользователя с таким идентификатором не существует");
            throw new NotFoundException("пользователя с таким идентификатором не существует");
        });
        Event event = eventRepository.findById(eventId).orElseThrow(() -> {
                    log.error("событие не найдено");
                    throw new NotFoundException("событие не найдено");
                }
        );
        Request request = new Request(0L, TimeMapper.timeToString(LocalDateTime.now()),
                event, user, "PENDING");
        return requestRepository.save(request);
    }

    @Override
    public Request cancel(long userId, long requestId) {
        Request request = requestRepository.findById(requestId).orElseThrow(() -> {
            log.error("заявка не найдена");
            throw new NotFoundException("заявка не найдена");
        });
        if (request.getRequester().getId() == userId) {
            request.setStatus("CANCELED");
            return request;
        } else {
            throw new NotFoundException("упс, какие-то проблемки");
        }
    }

    @Override
    public List<Request> getAllRequestUser(long userId) {
        return requestRepository.findAllByRequesterId(userId);
    }
}
