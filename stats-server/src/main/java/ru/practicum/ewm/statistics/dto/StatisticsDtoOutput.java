package ru.practicum.ewm.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatisticsDtoOutput {
    private String app;
    private String uri;
    private long hits;
}
