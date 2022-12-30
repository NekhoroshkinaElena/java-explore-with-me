package ru.practicum.ewm.statistics.service;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.statistics.dto.GetRequestForStatistics;
import ru.practicum.ewm.statistics.dto.StatisticsDtoInput;
import ru.practicum.ewm.statistics.dto.StatisticsDtoOutput;
import ru.practicum.ewm.statistics.mapper.StatisticsMapper;
import ru.practicum.ewm.statistics.mapper.TimeMapper;
import ru.practicum.ewm.statistics.model.QStatistics;
import ru.practicum.ewm.statistics.model.Statistics;
import ru.practicum.ewm.statistics.repository.StatisticRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final StatisticRepository statisticRepository;

    public StatisticsDtoOutput save(StatisticsDtoInput statisticsDtoInput) {
        Statistics statistics = statisticRepository.save(StatisticsMapper.toStatistic(statisticsDtoInput));
        return StatisticsMapper.statisticsDtoOutput(statistics, getHits(statistics));
    }

    public List<StatisticsDtoOutput> getStats(GetRequestForStatistics getRequestForStatistics) {
        QStatistics statistic = QStatistics.statistics;
        List<Predicate> predicates = new ArrayList<>();
        if (getRequestForStatistics.getStart() != null) {
            predicates.add(statistic.timestamp.after(TimeMapper.stringToTime(getRequestForStatistics.getStart())));
        }
        if (getRequestForStatistics.getEnd() != null) {
            predicates.add(statistic.timestamp.before(TimeMapper.stringToTime(getRequestForStatistics.getEnd())));
        }
        if (getRequestForStatistics.getUris() != null) {
            predicates.add(statistic.uri.in(getRequestForStatistics.getUris()));
        }
        Iterable<Statistics> requestIterable = statisticRepository
                .findAll(Objects.requireNonNull(ExpressionUtils.allOf(predicates)));

        List<Statistics> statistics = StreamSupport.stream(requestIterable.spliterator(), false)
                .collect(Collectors.toList());

        if (getRequestForStatistics.getUnique()) {
            statistics = statistics.stream().distinct().collect(Collectors.toList());
        }
        List<StatisticsDtoOutput> stats = new ArrayList<>();
        for (Statistics e : statistics) {
            stats.add(StatisticsMapper.statisticsDtoOutput(e, getHits(e)));
        }
        return stats;
    }

    private int getHits(Statistics statistics) {
        return statisticRepository.countAllByUri(statistics.getUri());
    }
}
