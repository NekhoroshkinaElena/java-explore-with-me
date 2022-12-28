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
        request.setUsers(users);
        request.setStates(states);
        request.setCategories(categories);
        request.setRangeStart(rangeStart);
        request.setRangeEnd(rangeEnd);
        request.setFrom(from);
        request.setSize(size);
        return request;
    }
}
