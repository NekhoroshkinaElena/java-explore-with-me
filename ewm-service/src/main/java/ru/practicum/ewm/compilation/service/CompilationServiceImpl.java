package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.model.CompilationInputDto;
import ru.practicum.ewm.compilation.model.CompilationMapper;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public Compilation postCompilation(CompilationInputDto compilationInputDto) {
        List<Event> events = new ArrayList<>();
        for (Long i : compilationInputDto.getEvents()) {
            events.add(eventRepository.findById(i).orElseThrow(() -> {
                log.error("событие с id " + i + " не найдено");
                return new NotFoundException("событие с id " + i + " не найдено");
            }));
        }
        Compilation compilation = CompilationMapper.toCompilation(compilationInputDto);
        compilation.setEvents(events);


        return compilationRepository.save(compilation);
    }

    @Override
    public void deleteCompilation(long id) {
        compilationRepository.deleteById(id);
    }

    @Override
    public void deleteEventFromCompilation(long compId, long eventId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() -> {
            log.error("такой подборки не существует");
            return new NotFoundException("такой подборки не существует");
        });
        Event event = eventRepository.findById(eventId).orElseThrow(() -> {
            log.error("событие с id " + eventId + " не найдено");
            return new NotFoundException("событие с id " + eventId + " не найдено");
        });

        compilation.getEvents().remove(event);
        compilationRepository.save(compilation);
    }

    @Override
    public Compilation getById(long id) {
        return compilationRepository.findById(id).orElseThrow(() -> {
            log.error("такой подборки не существует");
            return new NotFoundException("такой подборки не существует");
        });
    }

    @Override
    public List<Compilation> getAll(Boolean pinned, int from, int size) {
        return compilationRepository.findAllByPinned(pinned);
    }

    @Override
    public void addEvent(long compId, long eventId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() -> {
            log.error("такой подборки не существует");
            return new NotFoundException("такой подборки не существует");
        });
        Event event = eventRepository.findById(eventId).orElseThrow(() -> {
            log.error("событие с id " + eventId + " не найдено");
            return new NotFoundException("событие с id " + eventId + " не найдено");
        });
        compilation.getEvents().add(event);
        compilationRepository.save(compilation);
    }

    @Override
    public void pinEvent(long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() -> {
            log.error("такой подборки не существует");
            return new NotFoundException("такой подборки не существует");
        });
        compilation.setPinned(true);
        compilationRepository.save(compilation);
    }

    @Override
    public void cancelPinEvent(long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() -> {
            log.error("такой подборки не существует");
            return new NotFoundException("такой подборки не существует");
        });
        compilation.setPinned(false);
        compilationRepository.save(compilation);
    }
}
