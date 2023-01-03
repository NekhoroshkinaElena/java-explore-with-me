package ru.practicum.ewm.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.request.model.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByEventId(long eventId);

    List<Request> findAllByRequesterId(long userId);

    Request findRequestByEventIdAndRequesterId(long eventId, long userId);

    int countRequestByEventId(long eventId);
}
