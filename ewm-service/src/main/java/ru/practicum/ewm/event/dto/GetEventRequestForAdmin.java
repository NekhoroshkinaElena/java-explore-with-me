package ru.practicum.ewm.event.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.event.model.StateEvent;

import java.util.List;

@Data
@NoArgsConstructor
public class GetEventRequestForAdmin {
    private List<Long> users;
    private List<StateEvent> states;
    private List<Long> categories;
    private String rangeStart;
    private String rangeEnd;
    private int from;
    private int size;

    public static GetEventRequestForAdmin of(List<Long> users,
                                             List<StateEvent> states,
                                             List<Long> categories,
                                             String rangeStart,
                                             String rangeEnd,
                                             int from,
                                             int size
    ) {
        GetEventRequestForAdmin request = new GetEventRequestForAdmin();
        if (users != null) {
            request.setUsers(users);
        }
        if (states != null) {
            request.setStates(states);
        }
        if (categories != null) {
            request.setCategories(categories);
        }
        if (rangeStart != null) {
            request.setRangeStart(rangeStart);
        }
        if (rangeEnd != null) {
            request.setRangeEnd(rangeEnd);
        }
        request.setFrom(from);
        request.setSize(size);
        return request;
    }
}
