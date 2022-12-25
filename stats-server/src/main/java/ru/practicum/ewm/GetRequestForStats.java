package ru.practicum.ewm;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GetRequestForStats {
    private String start;
    private String end;
    private List<String> uris;
    private Boolean unique;

    public static GetRequestForStats of(String start, String end, List<String> uris, Boolean unique) {
        GetRequestForStats getRequestForStats = new GetRequestForStats();
        if (start != null) {
            getRequestForStats.setStart(start);
        }
        if (end != null) {
            getRequestForStats.setEnd(end);
        }
        if (uris != null) {
            getRequestForStats.setUris(uris);
        }
        getRequestForStats.setUnique(unique);
        return getRequestForStats;
    }
}
