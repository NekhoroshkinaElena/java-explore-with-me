package ru.practicum.ewm.compilation.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.compilation.dto.CompilationInputDto;
import ru.practicum.ewm.compilation.dto.CompilationOutputDto;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.Event;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CompilationMapper {

    public static Compilation createCompilation(CompilationInputDto compilationInputDto, List<Event> events) {
        return new Compilation(null, compilationInputDto.getTitle(),
                compilationInputDto.getPinned(), events);
    }

    public static CompilationOutputDto toCompilationOutputDto(Compilation compilation) {
        return new CompilationOutputDto(compilation.getId(), compilation.getTitle(),
                compilation.getPinned(),
                compilation.getEvents().stream().map(EventMapper::toEventDtoOutput).collect(Collectors.toList()));
    }
}
