package ru.practicum.ewm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatisticDtoOutput {
    private String app;
    private String uri;
    private long hits;
}
