package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.category.dto.CategoryDtoInput;
import ru.practicum.ewm.user.dto.UserShortDto;

@Getter
@Setter
@AllArgsConstructor
public class EvenShortDtoForUser {
    private String annotation;
    private CategoryDtoInput category;
    private int confirmedRequests;
    private String eventDate;
    private long id;
    private UserShortDto initiator;
    private Boolean paid;
    private String title;
    private int views;
}
