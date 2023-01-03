package ru.practicum.ewm.event.client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViewsForEvent {
    String app;
    String uri;
    Integer hits;
}
