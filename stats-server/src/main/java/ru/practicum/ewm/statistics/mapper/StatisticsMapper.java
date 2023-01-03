package ru.practicum.ewm.statistics.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.statistics.dto.StatisticsDtoInput;
import ru.practicum.ewm.statistics.dto.StatisticsDtoOutput;
import ru.practicum.ewm.statistics.model.Statistics;

@UtilityClass
public class StatisticsMapper {

    public static Statistics toStatistic(StatisticsDtoInput statisticsDtoInput) {
        return new Statistics(0L, statisticsDtoInput.getApp(),
                statisticsDtoInput.getUri(), statisticsDtoInput.getIp(),
                TimeMapper.stringToTime(statisticsDtoInput.getTimestamp()));
    }

    public static StatisticsDtoOutput statisticsDtoOutput(Statistics statistics, int hits) {
        return new StatisticsDtoOutput(statistics.getApp(), statistics.getUri(), hits);
    }
}
