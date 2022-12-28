package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationOutputDto;
import ru.practicum.ewm.compilation.service.CompilationService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class PublicCompilationController {
    private final CompilationService compilationService;

    @GetMapping("/{compId}")
    public CompilationOutputDto getById(@PathVariable long compId) {
        log.info("Получение подборки по id.");
        return compilationService.getById(compId);
    }

    @GetMapping
    public List<CompilationOutputDto> getAll(@RequestParam(value = "pinned", required = false, defaultValue = "false")
                                             Boolean pinned,
                                             @RequestParam(value = "from", required = false, defaultValue = "0")
                                             int from,
                                             @RequestParam(value = "size", required = false, defaultValue = "10")
                                             int size) {
        log.info("Получение подборок событий.");
        return compilationService.getAll(pinned, from, size);
    }
}
