package ru.practicum.ewm.event.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HitDtoInput {
    private Long id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
