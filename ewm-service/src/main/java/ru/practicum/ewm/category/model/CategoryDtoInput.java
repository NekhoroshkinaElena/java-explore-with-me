package ru.practicum.ewm.category.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class CategoryDtoInput {

    private long id;
    @NotNull
    @NotEmpty
    private String name;
}
