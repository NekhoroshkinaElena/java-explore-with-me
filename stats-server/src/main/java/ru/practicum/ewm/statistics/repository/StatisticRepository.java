package ru.practicum.ewm.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.ewm.statistics.model.Statistics;

public interface StatisticRepository extends JpaRepository<Statistics, Long>, QuerydslPredicateExecutor<Statistics> {
    int countAllByApp(String app);
}
