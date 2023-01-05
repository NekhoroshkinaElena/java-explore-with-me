package ru.practicum.ewm.event.comment;

import lombok.Value;

@Value
public class CommentDtoOutput {
    long id;
    String text;
    String authorName;
    String created;
}
