package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.category.dto.CategoryDtoOutput;
import ru.practicum.ewm.event.comment.CommentDtoOutput;
import ru.practicum.ewm.event.location.LocationDto;
import ru.practicum.ewm.event.model.StateEvent;
import ru.practicum.ewm.user.dto.UserShortDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EventOutputDtoWithComments {
    private long id;
    private String annotation;
    private CategoryDtoOutput category;
    private int confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    private UserShortDto initiator;
    private LocationDto location;
    private Boolean paid;
    private int participantLimit;
    private String publishedOn;
    private Boolean requestModeration;
    private StateEvent state;
    private String title;
    private int views;
    private List<CommentDtoOutput> comments;
}
