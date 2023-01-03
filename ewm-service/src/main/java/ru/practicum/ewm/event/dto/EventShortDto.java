package ru.practicum.ewm.event.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventShortDto {
    private String annotation;
    private long category;
    private String description;
    private String eventDate;
    private long eventId;
    private Boolean paid;
    private int participantLimit;
    private String title;
}
