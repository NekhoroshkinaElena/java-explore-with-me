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
import ru.practicum.ewm.event.dto.*;
import ru.practicum.ewm.event.location.LocationRepository;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.mapper.TimeMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.QEvent;
import ru.practicum.ewm.event.model.Sort;
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

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    public EventOutputDto save(NewEventDto newEventDto, long userId) {
        Event event = EventMapper.toEvent(newEventDto);
        timeValidate(newEventDto.getEventDate());
        locationRepository.save(newEventDto.getLocation());
        event.setCategory(getCategory(newEventDto.getCategory()));
        event.setInitiator(getUser(userId));
        return EventMapper.toEventDtoOutput(eventRepository.save(event));
    }

    @Override
    public EventOutputDto publish(long eventId) {
        Event event = getEvent(eventId);
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            log.error("Дата начала события должна быть не ранее чем за час от даты публикации.");
            throw new ValidationException("Дата начала события должна быть не ранее чем за час от даты публикации.");
        }
        if (event.getState().equals(StateEvent.CANCELED) || event.getState().equals(StateEvent.PUBLISHED)) {
            log.error("Событие должно быть в состоянии ожидания публикации.");
            throw new ValidationException("Событие должно быть в состоянии ожидания публикации.");
        }
        event.setState(StateEvent.PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());
        eventRepository.save(event);
        return EventMapper.toEventDtoOutput(event);
    }

    @Override
    public EventOutputDto rejectAdmin(long eventId) {
        Event event = getEvent(eventId);
        if (event.getState().equals(StateEvent.PUBLISHED)) {
            log.error("Нельзя отменить опубликованное событие.");
            throw new ValidationException("Нельзя отменить опубликованное событие.");
        }
        event.setState(StateEvent.CANCELED);
        return EventMapper.toEventDtoOutput(event);
    }

    @Override
    public EventOutputDto editAdmin(long eventId, NewEventDto newEventDto) {
        Event event = getEvent(eventId);
        if (!newEventDto.getAnnotation().isEmpty()) {
            event.setAnnotation(newEventDto.getAnnotation());
        }
        if (newEventDto.getCategory() != 0) {
            event.setCategory(getCategory(newEventDto.getCategory()));
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
        return EventMapper.toEventDtoOutput(event);
    }

    @Override
    public EventOutputDto editUser(long userId, EventShortDto eventShortDto) {
        Event event = getEvent(eventShortDto.getEventId());
        if (event.getState().equals(StateEvent.PUBLISHED)) {
            log.error("Нельзя отредактировать опубликованное событие");
            throw new ValidationException("Нельзя отредактировать опубликованное событие");
        }
        if (event.getState().equals(StateEvent.CANCELED)) {
            event.setState(StateEvent.PENDING);
        }
        timeValidate(eventShortDto.getEventDate());

        validateInitiator(event.getInitiator().getId(), userId);
        if (!eventShortDto.getAnnotation().isEmpty()) {
            event.setAnnotation(eventShortDto.getAnnotation());
        }
        if (eventShortDto.getCategory() != 0) {
            event.setCategory(getCategory(eventShortDto.getCategory()));
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
        return EventMapper.toEventDtoOutput(event);
    }

    @Override
    public List<EvenShortDtoForUser> getAllForUser(long userId, int from, int size) {
        getUser(userId);
        return eventRepository.findAllByInitiatorId(userId, PageRequest.of(from / size, size)).stream()
                .map(EventMapper::toEventDtoForUser).collect(Collectors.toList());
    }

    @Override
    public EventOutputDto getByIdForUser(long userId, long eventId) {
        Event event = getEvent(eventId);
        validateInitiator(event.getInitiator().getId(), userId);
        return EventMapper.toEventDtoOutput(event);
    }

    @Override
    public EventOutputDto rejectUser(long userId, long eventId) {
        Event event = getEvent(eventId);
        validateInitiator(event.getInitiator().getId(), userId);
        if (event.getState().equals(StateEvent.PUBLISHED)) {
            log.error("Нельзя отменить опубликованное событие");
            throw new ValidationException("Нельзя отменить опубликованное событие");
        }
        event.setState(StateEvent.CANCELED);
        return EventMapper.toEventDtoOutput(event);
    }

    @Override
    public RequestDto confirmRequestUser(long userId, long eventId, long reqId) {
        Event event = getEvent(eventId);
        Request request = getRequest(reqId);
        getUser(userId);
        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            log.error("Подтверждение заявок на участие в этом событии не требуется.");
            throw new ValidationException("Подтверждение заявок на участие в этом событии не требуется.");
        }
        if (event.getParticipantLimit() <= event.getConfirmedRequest()) {
            log.info("Заявка на участие в событии отклонена, так как превышен лимит заявок.");
            request.setStatus("CANCELED");
        }
        request.setStatus("CONFIRMED");
        return RequestMapper.toRequestDto(request);
    }

    @Override
    public RequestDto rejectRequestUser(long userId, long eventId, long reqId) {
        Request request = getRequest(reqId);
        getUser(userId);
        getEvent(eventId);
        request.setStatus("REJECTED");
        return RequestMapper.toRequestDto(request);
    }

    @Override
    public List<RequestDto> getAllRequestForEvent(long userId, long eventId) {
        Event event = getEvent(eventId);
        getUser(userId);
        validateInitiator(event.getInitiator().getId(), userId);
        return requestRepository.findAllByEventId(eventId).stream()
                .map(RequestMapper::toRequestDto).collect(Collectors.toList());
    }

    @Override
    public EventOutputDto getEventById(long eventId, String uri, String ip) {
        eventClient.saveRequestInStatistic(uri, ip);
        Event event = getEvent(eventId);
        if (!event.getState().equals(StateEvent.PUBLISHED)) {
            log.error("Нельзя получить информацию о неопубликованном событии.");
            throw new ValidationException("Нельзя получить информацию о неопубликованном событии.");
        }
        event.setViews(event.getViews() + 1);
        eventRepository.save(event);
        return EventMapper.toEventDtoOutput(event);
    }

    @Override
    public List<EventOutputDto> searchEventsAdmin(GetEventRequestForAdmin request) {
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
        Iterable<Event> eventsIterable = eventRepository
                .findAll(Objects.requireNonNull(ExpressionUtils.allOf(predicates)),
                        PageRequest.of(request.getFrom(), request.getSize()));
        List<Event> events = new ArrayList<>();
        for (Event e : eventsIterable) {
            events.add(e);
        }
        return events.stream().map(EventMapper::toEventDtoOutput).collect(Collectors.toList());
    }

    @Override
    public List<EventOutputDto> getEventsForAll(GetEventRequestForAll request, String uri, String ip) {
        eventClient.saveRequestInStatistic(uri, ip);
        QEvent event = QEvent.event;
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(event.state.eq(StateEvent.PUBLISHED));
        if (!request.getText().isEmpty()) {
            predicates.add(event.annotation.likeIgnoreCase(request.getText()));
        }
        if (request.getCategories() != null) {
            predicates.add(event.category.id.in(request.getCategories()));
        }
        if (request.getPaid() != null) {
            predicates.add(event.paid.eq(request.getPaid()));
        }
        if (request.getRangeStart() != null) {
            predicates.add(event.eventDate.after(TimeMapper.stringToTime(request.getRangeStart())));
        } else {
            predicates.add(event.eventDate.after(LocalDateTime.now()));
        }
        if (request.getRangeEnd() != null) {
            predicates.add(event.eventDate.before(TimeMapper.stringToTime(request.getRangeEnd())));
        }
        if (request.getOnlyAvailable()) {
            predicates.add(event.participantLimit.eq(0));
        }

        Iterable<Event> eventsIterable = eventRepository
                .findAll(Objects.requireNonNull(ExpressionUtils.allOf(predicates)),
                        PageRequest.of(request.getFrom(), request.getSize()));
        List<EventOutputDto> events = new LinkedList<>();
        for (Event e : eventsIterable) {
            events.add(EventMapper.toEventDtoOutput(e));
        }
        if (request.getSort() != null) {
            if (request.getSort().equals(Sort.VIEWS)) {
                events.sort(Comparator.comparingInt(EventOutputDto::getViews).reversed());
            } else {
                events.sort((Comparator.comparing(EventOutputDto::getEventDate)));
            }
        }
        return events;
    }

    private Category getCategory(long categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> {
            log.error("Категории с id " + categoryId + " не существует.");
            return new NotFoundException("Категории с id " + categoryId + " не существует.");
        });
    }

    private User getUser(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            log.error("Пользователь с id" + userId + " не найден.");
            return new NotFoundException("Пользователь с id" + userId + " не найден.");
        });
    }

    private Event getEvent(long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> {
            log.error("Событие с id " + eventId + " не найдено.");
            return new NotFoundException("Событие с id " + eventId + " не найдено.");
        });
    }

    private Request getRequest(long requestId) {
        return requestRepository.findById(requestId).orElseThrow(() -> {
            log.error("Заявка с id " + requestId + " не найдена.");
            return new NotFoundException("Заявка с id " + requestId + " не найдена.");
        });
    }

    private void timeValidate(String time) {
        if (TimeMapper.stringToTime(time).isBefore(LocalDateTime.now().plusHours(2))) {
            log.error("Дата и время события не могут быть раньше, чем через два часа от текущего момента.");
            throw new ValidationException("Дата и время события не могут быть раньше, чем через два часа от " +
                    "текущего момента.");
        }
    }

    private void validateInitiator(long initiatorId, long userId) {
        if (initiatorId != userId) {
            log.error("Вы не можете выполнять действия над событием (редактировать, отменять, получать " +
                    "подробную информацию о событии) так как не являеесь его создателем.");
            throw new NotFoundException("Вы не можете выполнять действия над событием (редактировать, отменять," +
                    " получать подробную информацию о событии) так как не являеесь его создателем.");
        }
    }
}
