package ru.practicum.ewm;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RequestsService {
    private final RequestsRepository requestsRepository;

    public Request save(RequestDtoInput requestDtoInput) {
        Request request = new Request(0L, requestDtoInput.getApp(),
                requestDtoInput.getUri(), requestDtoInput.getIp(),
                TimeMapper.stringToTime(requestDtoInput.getTimestamp()));
        return requestsRepository.save(request);
    }

    public List<StatisticDtoOutput> getStats(GetRequestForStats getRequestForStats) {
        QRequest request = QRequest.request;
        List<Predicate> predicates = new ArrayList<>();
        if (getRequestForStats.getStart() != null) {
            predicates.add(request.timestamp.after(TimeMapper.stringToTime(getRequestForStats.getStart())));
        }
        if (getRequestForStats.getEnd() != null) {
            predicates.add(request.timestamp.before(TimeMapper.stringToTime(getRequestForStats.getEnd())));
        }
        if (getRequestForStats.getUris() != null) {
            predicates.add(request.uri.in(getRequestForStats.getUris()));
        }
        Iterable<Request> requestIterable = requestsRepository
                .findAll(Objects.requireNonNull(ExpressionUtils.allOf(predicates)));

        List<StatisticDtoOutput> stats = new ArrayList<>();
        for (Request e : requestIterable) {
            stats.add(new StatisticDtoOutput(e.getApp(), e.getUri(), requestsRepository.countAllByApp(e.getApp())));
        }
        return stats;
    }
}
