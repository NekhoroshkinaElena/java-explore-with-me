package ru.practicum.ewm.category.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class NewCategoryDto {
    @NotEmpty
    @NotNull
    private String name;
}
