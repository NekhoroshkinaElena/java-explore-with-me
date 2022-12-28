package ru.practicum.ewm.compilation.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.compilation.dto.CompilationInputDto;
import ru.practicum.ewm.compilation.dto.CompilationOutputDto;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.event.mapper.EventMapper;

import java.util.stream.Collectors;

@UtilityClass
public class CompilationMapper {

    public static Compilation toCompilation(CompilationInputDto compilationInputDto) {
        return new Compilation(0L, compilationInputDto.getTitle(),
                compilationInputDto.getPinned(), null);
    }

    public static CompilationOutputDto toCompilationOutputDto(Compilation compilation) {
        return new CompilationOutputDto(compilation.getId(), compilation.getTitle(),
                compilation.getPinned(),
                compilation.getEvents().stream().map(EventMapper::toEventDtoOutput).collect(Collectors.toList()));
    }
}
