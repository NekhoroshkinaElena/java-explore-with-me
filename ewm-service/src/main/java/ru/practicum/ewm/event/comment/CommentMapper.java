package ru.practicum.ewm.event.comment;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.event.mapper.TimeMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.model.User;

import java.time.LocalDateTime;

@UtilityClass
public class CommentMapper {

    public static Comment createComment(CommentDtoInput commentDtoInput, Event event, User user) {
        return new Comment(0L, commentDtoInput.getText(), event, user, LocalDateTime.now());
    }

    public static CommentDtoOutput toCommentDtoOutput(Comment comment) {
        return new CommentDtoOutput(comment.getId(), comment.getText(), comment.getAuthor().getName(),
                TimeMapper.timeToString(comment.getCreated()));
    }
}
