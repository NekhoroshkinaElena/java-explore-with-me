package ru.practicum.ewm.event.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByEventId(long eventId);

    List<Comment> getAllByTextContainsIgnoreCase(String text);
}
