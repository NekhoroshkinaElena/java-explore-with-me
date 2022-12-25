package ru.practicum.ewm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestsRepository extends JpaRepository<Request, Long>, QuerydslPredicateExecutor<Request> {
    int countAllByApp(String app);
}
