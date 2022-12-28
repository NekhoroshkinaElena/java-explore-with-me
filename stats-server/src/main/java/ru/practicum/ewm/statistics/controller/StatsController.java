package ru.practicum.ewm.statistics.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.statistics.dto.GetRequestForStatistics;
import ru.practicum.ewm.statistics.dto.StatisticsDtoInput;
import ru.practicum.ewm.statistics.dto.StatisticsDtoOutput;
import ru.practicum.ewm.statistics.service.StatisticsServiceImpl;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatsController {
    private final StatisticsServiceImpl statisticsServiceImpl;

    @PostMapping("/hit")
    public StatisticsDtoOutput save(@RequestBody StatisticsDtoInput request) {
        log.info("Сохранение информации о том, что к эндпоинту был запрос.");
        return statisticsServiceImpl.save(request);
    }

    @GetMapping("/stats")
    public List<StatisticsDtoOutput> get(@RequestParam(value = "uris", required = false) List<String> uris,
                                         @RequestParam(value = "unique", required = false, defaultValue = "false")
                                         Boolean unique,
                                         @RequestParam(value = "start", required = false) String start,
                                         @RequestParam(value = "end", required = false) String end) {
        log.info("Получение статистики по посещениям.");
        return statisticsServiceImpl.getStats(GetRequestForStatistics.of(start, end, uris, unique));
    }
}
