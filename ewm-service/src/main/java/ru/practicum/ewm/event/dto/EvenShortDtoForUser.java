package ru.practicum.ewm.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.category.model.CategoryDtoInput;
import ru.practicum.ewm.user.model.UserOutPutDto;

@Getter
@Setter
@AllArgsConstructor
public class EvenShortDtoForUser {
    private String annotation;
    private CategoryDtoInput category;
    private int confirmedRequests;
    private String eventDate;
    private long id;
    private UserOutPutDto initiator;
    private Boolean paid;
    private String title;
    private int views;
}
