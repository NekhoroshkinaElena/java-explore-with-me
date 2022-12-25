package ru.practicum.ewm.compilation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.event.dto.EventOutputDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CompilationOutputDto {
    private long id;
    private String title;
    private Boolean pinned;
    private List<EventOutputDto> events;
}
