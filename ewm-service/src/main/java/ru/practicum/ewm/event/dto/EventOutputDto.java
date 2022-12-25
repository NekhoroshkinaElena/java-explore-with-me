package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.event.location.Location;
import ru.practicum.ewm.event.model.StateEvent;
import ru.practicum.ewm.user.model.UserOutPutDto;

@Getter
@Setter
@AllArgsConstructor
public class EventOutputDto {
    private long id;
    private String annotation;
    private Category category;
    private int confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    private UserOutPutDto initiator;
    private Location location;
    private Boolean paid;
    private int participantLimit;
    private String publishedOn;
    private Boolean requestModeration;
    private StateEvent state;
    private String title;
    private int views;
}
