package ru.practicum.ewm.user.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserShortDto {
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String email;
}
