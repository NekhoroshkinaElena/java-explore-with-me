package ru.practicum.ewm.event.service;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.event.client.EventClient;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.GetEventRequestForAdmin;
import ru.practicum.ewm.event.dto.GetEventRequestForAll;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.location.LocationRepository;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.mapper.TimeMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.QEvent;
import ru.practicum.ewm.event.model.StateEvent;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final RequestRepository requestRepository;
    private final EventClient eventClient;

    @Override
    public Event save(NewEventDto newEventDto, long userId) {

        locationRepository.save(newEventDto.getLocation());
        Event event = EventMapper.toEvent(newEventDto);
        event.setCategory(categoryRepository.findById(newEventDto.getCategory()).orElseThrow(() -> {
            log.error("Категории с id" + newEventDto.getCategory() + "не существует");
            return new NotFoundException("Категории с id " + newEventDto.getCategory() + "не существует");
        }));

        event.setInitiator(userRepository.findById(userId).orElseThrow(() -> {
            log.error("Такого пользователя не существует");
            return new NotFoundException("Такого пользователя не существует");
        }));
        return eventRepository.save(event);
    }

    @Override
    public Event publish(long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> {
            log.error("Такого события не существует");
            return new NotFoundException("Такого события не существует");
        });
        event.setState(StateEvent.PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());
        eventRepository.save(event);
        return event;
    }

    @Override
    public Event rejectAdmin(long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> {
            log.error("Такого события не существует");
            return new NotFoundException("Такого события не существует");
        });
        event.setState(StateEvent.CANCELED);
        return event;
    }

    @Override
    public Event editAdmin(long eventId, NewEventDto newEventDto) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> {
            log.error("Такого события не существует");
            return new NotFoundException("Такого события не существует");
        });

        if (!newEventDto.getAnnotation().isEmpty()) {
            event.setAnnotation(newEventDto.getAnnotation());
        }

        if (newEventDto.getCategory() != 0) {
            Category category = categoryRepository.findById(newEventDto.getCategory()).orElseThrow(() -> {
                log.error("Категории с id" + newEventDto.getCategory() + "не существует");
                return new NotFoundException("Категории с id " + newEventDto.getCategory() + "не существует");
            });
            event.setCategory(category);
        }

        if (!newEventDto.getDescription().isEmpty()) {
            event.setDescription(newEventDto.getDescription());
        }

        if (!newEventDto.getEventDate().isEmpty() || newEventDto.getEventDate() != null) {
            event.setEventDate(TimeMapper.stringToTime(newEventDto.getEventDate()));
        }

        if (newEventDto.getLocation() != null) {
            event.setLocation(newEventDto.getLocation());
        }
        event.setPaid(newEventDto.getPaid());
        if (newEventDto.getParticipantLimit() != 0) {
            event.setParticipantLimit(newEventDto.getParticipantLimit());
        }

        if (newEventDto.getRequestModeration() != null) {
            event.setRequestModeration(newEventDto.getRequestModeration());
        }

        if (!newEventDto.getTitle().isEmpty()) {
            event.setTitle(newEventDto.getTitle());
        }

        return event;
    }

    @Override
    public Event editUser(long userId, EventShortDto eventShortDto) {
        Event event = eventRepository.findById(eventShortDto.getEventId()).orElseThrow(() -> {
            log.error("Такого события не существует");
            return new NotFoundException("Такого события не существует");
        });
        if (event.getInitiator().getId() == userId) {
            if (!eventShortDto.getAnnotation().isEmpty()) {
                event.setAnnotation(eventShortDto.getAnnotation());
            }
            if (eventShortDto.getCategory() != 0) {
                Category category = categoryRepository.findById(eventShortDto.getCategory()).orElseThrow(() -> {
                    log.error("Категории с id" + eventShortDto.getCategory() + "не существует");
                    return new NotFoundException("Категории с id " + eventShortDto.getCategory() + "не существует");
                });
            }
            if (!eventShortDto.getDescription().isEmpty()) {
                event.setDescription(eventShortDto.getDescription());
            }
            if (eventShortDto.getPaid() != null) {
                event.setPaid(eventShortDto.getPaid());
            }
            if (eventShortDto.getParticipantLimit() != 0) {
                event.setParticipantLimit(eventShortDto.getParticipantLimit());
            }
            if (!eventShortDto.getTitle().isEmpty()) {
                event.setTitle(eventShortDto.getTitle());
            }
        }
        return event;
    }

    @Override
    public List<Event> getAllForUser(long userId) {
        return eventRepository.findAllByInitiatorId(userId);
    }

    @Override
    public Event getByIdForUser(long userId, long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> {
            log.error("Такого события не существует");
            return new NotFoundException("Такого события не существует");
        });
        if (event.getInitiator().getId() != userId) {
            log.error("Вы не можете получить полную информацию о данном сбытии");
            throw new NotFoundException("Вы не можете получить полную информацию о данном сбытии");
        }
        return event;
    }

    @Override
    public Event rejectEventUser(long userId, long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> {
            log.error("Такого события не существует");
            return new NotFoundException("Такого события не существует");
        });
        if (event.getInitiator().getId() != userId) {
            log.error("вы не можете отклонить событие, так как не вы его создавали, атата");
            throw new NotFoundException("вы не можете отклонить событие, так как не вы его создавали, атата");
        }
        event.setState(StateEvent.CANCELED);
        return event;
    }

    @Override
    public Request confirmRequestUser(long userId, long eventId, long reqId) {
        Request request = requestRepository.findById(reqId).orElseThrow(() -> {
            log.error("такой заявки не существует");
            return new NotFoundException("такой заявки не существует");
        });
        request.setStatus("CONFIRMED");
        return request;
    }

    @Override
    public Request rejectRequestUser(long userId, long eventId, long reqId) {
        Request request = requestRepository.findById(reqId).orElseThrow(() -> {
            log.error("такой заявки не существует");
            return new NotFoundException("такой заявки не существует");
        });
        request.setStatus("REJECTED");
        return request;
    }

    @Override
    public List<Request> getAllRequestForEventUser(long userId, long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> {
            log.error("Такого события не существует");
            return new NotFoundException("Такого события не существует");
        });
        if (event.getInitiator().getId() != userId) {
            log.error("вы не можете отклонить событие, так как не вы его создавали, атата");
            throw new NotFoundException("вы не можете отклонить событие, так как не вы его создавали, атата");
        }
        return requestRepository.findAllByEventId(eventId);
    }

    @Override
    public Event getEventById(long eventId, String uri, String ip) {
        eventClient.saveRequestInStatistic(uri, ip);
        Event event = eventRepository.findById(eventId).orElseThrow(() -> {
            log.error("Такого события не существует");
            return new NotFoundException("Такого события не существует");
        });
        event.setViews(event.getViews() + 1);
        eventRepository.save(event);
        return event;
    }

    @Override
    public List<Event> searchEvents(GetEventRequestForAdmin request) {
        QEvent event = QEvent.event;
        List<Predicate> predicates = new ArrayList<>();
        if (request.getStates() != null) {
            predicates.add(event.state.in(request.getStates()));
        }
        if (request.getUsers() != null) {
            predicates.add(event.initiator.id.in(request.getUsers()));
        }
        if (request.getCategories() != null) {
            predicates.add((event.category.id.in(request.getCategories())));
        }
        if (request.getRangeStart() != null) {
            predicates.add(event.eventDate.after(TimeMapper.stringToTime(request.getRangeStart())));
        }
        if (request.getRangeEnd() != null) {
            predicates.add(event.eventDate.before(TimeMapper.stringToTime(request.getRangeEnd())));
        }
        PageRequest pageRequest = PageRequest.of(request.getFrom(), request.getSize());
        Iterable<Event> eventsIterable = eventRepository
                .findAll(Objects.requireNonNull(ExpressionUtils.allOf(predicates)), pageRequest);
        List<Event> events = new ArrayList<>();
        for (Event e : eventsIterable) {
            events.add(e);
        }
        return events;
    }

    @Override
    public List<Event> getEventForAll(GetEventRequestForAll request, String uri, String ip) {
        eventClient.saveRequestInStatistic(uri, ip);
        QEvent event = QEvent.event;
        List<Predicate> predicates = new ArrayList<>();
        if (!request.getText().isEmpty()) {
            predicates.add(event.annotation.likeIgnoreCase(request.getText()));
        }
        PageRequest pageRequest = PageRequest.of(request.getFrom(), request.getSize());
        Iterable<Event> eventsIterable = eventRepository
                .findAll(Objects.requireNonNull(ExpressionUtils.allOf(predicates)), pageRequest);
        List<Event> events = new ArrayList<>();
        for (Event e : eventsIterable) {
            events.add(e);
        }
        return events;
    }
}
