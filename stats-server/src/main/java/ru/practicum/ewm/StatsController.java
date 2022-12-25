package ru.practicum.ewm;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatsController {
    private final RequestsService requestsService;

    @PostMapping("/hit")
    public Request save(@RequestBody RequestDtoInput request) {
        return requestsService.save(request);
    }

    @GetMapping("/stats")
    public List<StatisticDtoOutput> get(@RequestParam(value = "uris", required = false) List<String> uris,
                                        @RequestParam(value = "unique", required = false) Boolean unique,
                                        @RequestParam(value = "start", required = false) String start,
                                        @RequestParam(value = "end", required = false) String end) {
        return requestsService.getStats(GetRequestForStats.of(start, end, uris, unique));
    }
}
