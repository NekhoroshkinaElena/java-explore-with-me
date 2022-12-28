package ru.practicum.ewm.compilation.dto;

import lombok.Value;
import ru.practicum.ewm.event.dto.EventOutputDto;

import java.util.List;

@Value
public class CompilationOutputDto {
    long id;
    String title;
    Boolean pinned;
    List<EventOutputDto> events;
}
