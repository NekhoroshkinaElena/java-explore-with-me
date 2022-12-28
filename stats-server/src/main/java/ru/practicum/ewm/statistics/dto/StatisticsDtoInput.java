package ru.practicum.ewm.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class StatisticsDtoInput {
    private long id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
