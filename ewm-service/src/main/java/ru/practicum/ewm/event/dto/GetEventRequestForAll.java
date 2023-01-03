package ru.practicum.ewm.event.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.event.model.Sort;

import java.util.List;

@Data
@NoArgsConstructor
public class GetEventRequestForAll {
    private String text;
    private List<Long> categories;
    private Boolean paid;
    private String rangeStart;
    private String rangeEnd;
    private Boolean onlyAvailable;
    private Sort sort;
    private int from;
    private int size;


    public static GetEventRequestForAll of(
            String text,
            List<Long> categories,
            Boolean paid,
            String rangeStart,
            String rangeEnd,
            Boolean onlyAvailable,
            Sort sort,
            int from,
            int size
    ) {
        GetEventRequestForAll request = new GetEventRequestForAll();
        request.setPaid(paid);
        request.setOnlyAvailable(onlyAvailable);
        request.setText(text);
        request.setSort(sort);
        request.setCategories(categories);
        request.setRangeStart(rangeStart);
        request.setRangeEnd(rangeEnd);
        request.setFrom(from);
        request.setSize(size);
        return request;
    }
}
