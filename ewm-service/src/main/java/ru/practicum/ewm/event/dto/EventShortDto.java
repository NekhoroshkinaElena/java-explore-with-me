package ru.practicum.ewm.event.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventShortDto {
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Long eventId;
    private Boolean paid;
    private int participantLimit;
    private String title;
}
