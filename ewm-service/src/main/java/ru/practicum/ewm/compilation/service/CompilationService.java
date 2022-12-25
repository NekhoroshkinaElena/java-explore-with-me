package ru.practicum.ewm.compilation.service;

import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.model.CompilationInputDto;

import java.util.List;

public interface CompilationService {

    Compilation postCompilation(CompilationInputDto compilation);

    void deleteCompilation(long id);

    void deleteEventFromCompilation(long compId, long eventId);

    Compilation getById(long id);

    List<Compilation> getAll(Boolean pinned, int from, int size);

    void addEvent(long compId, long eventId);

    void pinEvent(long compId);

    void cancelPinEvent(long compId);
}
