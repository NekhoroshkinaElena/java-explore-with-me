package ru.practicum.ewm.compilation.service;

import ru.practicum.ewm.compilation.dto.CompilationInputDto;
import ru.practicum.ewm.compilation.dto.CompilationOutputDto;

import java.util.List;

public interface CompilationService {

    CompilationOutputDto post(CompilationInputDto compilation);

    void delete(long id);

    void deleteEventFromCompilation(long compId, long eventId);

    CompilationOutputDto getById(long id);

    List<CompilationOutputDto> getAll(Boolean pinned, int from, int size);

    void addEvent(long compId, long eventId);

    void pinEvent(long compId);

    void cancelPinEvent(long compId);
}
