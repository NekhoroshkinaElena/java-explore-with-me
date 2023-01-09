package ru.practicum.ewm.event.comment;

import lombok.Value;

@Value
public class CommentDtoOutput {
    Long id;
    String text;
    String authorName;
    String created;
}
