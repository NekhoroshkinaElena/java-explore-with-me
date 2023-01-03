package ru.practicum.ewm.statistics.service;

import ru.practicum.ewm.statistics.dto.GetRequestForStatistics;
import ru.practicum.ewm.statistics.dto.StatisticsDtoInput;
import ru.practicum.ewm.statistics.dto.StatisticsDtoOutput;

import java.util.List;

public interface StatisticsService {

    StatisticsDtoOutput save(StatisticsDtoInput statisticsDtoInput);

    List<StatisticsDtoOutput> getStats(GetRequestForStatistics getRequestForStatistics);
}
