package ru.practicum.ewm.request.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    private long id;
    private long requester;
    private long event;
    private String status;
    private String created;
}
