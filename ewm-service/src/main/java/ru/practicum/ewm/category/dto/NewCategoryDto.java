package ru.practicum.ewm.category.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class NewCategoryDto {
    @NotEmpty
    private String name;
}
