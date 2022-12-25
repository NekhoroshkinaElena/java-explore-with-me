package ru.practicum.ewm.event.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.event.location.Location;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class NewEventDto {
    @Length(min = 20, max = 2000)
    @NotNull
    private String annotation;
    @NotNull
    private long category;
    @Length(min = 20, max = 7000)
    private String description;
    @NotNull
    @NotEmpty
    private String eventDate;
    @NotNull
    private Location location;
    private Boolean paid;
    private int participantLimit;
    private Boolean requestModeration;
    @Length(min = 3, max = 120)
    private String title;
}
