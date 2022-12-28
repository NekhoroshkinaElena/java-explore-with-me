package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.compilation.dto.CompilationInputDto;
import ru.practicum.ewm.compilation.dto.CompilationOutputDto;
import ru.practicum.ewm.compilation.mapper.CompilationMapper;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public CompilationOutputDto post(CompilationInputDto compilationInputDto) {
        List<Event> events = new ArrayList<>();
        for (Long i : compilationInputDto.getEvents()) {
            events.add(getEvent(i));
        }
        Compilation compilation = CompilationMapper.toCompilation(compilationInputDto);
        compilation.setEvents(events);
        return CompilationMapper.toCompilationOutputDto(compilationRepository.save(compilation));
    }

    @Override
    public void delete(long id) {
        getCompilation(id);
        compilationRepository.deleteById(id);
    }

    @Override
    public void deleteEventFromCompilation(long compId, long eventId) {
        Compilation compilation = getCompilation(compId);
        Event event = getEvent(eventId);
        compilation.getEvents().remove(event);
        compilationRepository.save(compilation);
    }

    @Override
    public CompilationOutputDto getById(long id) {
        return CompilationMapper.toCompilationOutputDto(getCompilation(id));
    }

    @Override
    public List<CompilationOutputDto> getAll(Boolean pinned, int from, int size) {
        return compilationRepository.findAllByPinned(pinned, PageRequest.of(from, size)).stream()
                .map(CompilationMapper::toCompilationOutputDto).collect(Collectors.toList());
    }

    @Override
    public void addEvent(long compId, long eventId) {
        Compilation compilation = getCompilation(compId);
        Event event = getEvent(eventId);
        compilation.getEvents().add(event);
        compilationRepository.save(compilation);
    }

    @Override
    public void pinEvent(long compId) {
        Compilation compilation = getCompilation(compId);
        compilation.setPinned(true);
        compilationRepository.save(compilation);
    }

    @Override
    public void cancelPinEvent(long compId) {
        Compilation compilation = getCompilation(compId);
        compilation.setPinned(false);
        compilationRepository.save(compilation);
    }

    private Compilation getCompilation(long compId) {
        return compilationRepository.findById(compId).orElseThrow(() -> {
            log.error("такой подборки не существует");
            return new NotFoundException("такой подборки не существует");
        });
    }

    private Event getEvent(long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() -> {
            log.error("событие с id " + eventId + " не найдено");
            return new NotFoundException("событие с id " + eventId + " не найдено");
        });
    }
}
