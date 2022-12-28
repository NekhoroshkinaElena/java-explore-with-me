package ru.practicum.ewm.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class CompilationInputDto {
    @NotNull
    private List<Long> events;
    @NotNull
    private Boolean pinned;
    @NotNull
    private String title;
}
