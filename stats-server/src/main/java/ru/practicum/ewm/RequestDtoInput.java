package ru.practicum.ewm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class RequestDtoInput {
    private long id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
