package ru.practicum.ewm.statistics.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GetRequestForStatistics {
    private String start;
    private String end;
    private List<String> uris;
    private Boolean unique;

    public static GetRequestForStatistics of(String start, String end, List<String> uris, Boolean unique) {
        GetRequestForStatistics getRequestForStatistics = new GetRequestForStatistics();
        if (start != null) {
            getRequestForStatistics.setStart(start);
        }
        if (end != null) {
            getRequestForStatistics.setEnd(end);
        }
        if (uris != null) {
            getRequestForStatistics.setUris(uris);
        }
        getRequestForStatistics.setUnique(unique);
        return getRequestForStatistics;
    }
}
