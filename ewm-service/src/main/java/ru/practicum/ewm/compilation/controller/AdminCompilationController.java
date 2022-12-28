package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.dto.CompilationInputDto;
import ru.practicum.ewm.compilation.dto.CompilationOutputDto;
import ru.practicum.ewm.compilation.service.CompilationService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class AdminCompilationController {
    private final CompilationService compilationService;

    @PostMapping
    public CompilationOutputDto post(@Valid @RequestBody CompilationInputDto compilationInputDto) {
        log.info("Добавление новой подборки.");
        return compilationService.post(compilationInputDto);
    }

    @DeleteMapping("/{compId}")
    public void delete(@PathVariable long compId) {
        log.info("Удаление подборки.");
        compilationService.delete(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventFromCompilations(@PathVariable long compId, @PathVariable long eventId) {
        log.info("Удаление события из подборки.");
        compilationService.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventInCompilations(@PathVariable long compId, @PathVariable long eventId) {
        log.info("Добавление события в подборку.");
        compilationService.addEvent(compId, eventId);
    }

    @PatchMapping("/{compId}/pin")
    public void pinCompilations(@PathVariable long compId) {
        log.info("Закрепление подборки на главной странице.");
        compilationService.pinEvent(compId);
    }

    @DeleteMapping("/{compId}/pin")
    public void cancelPinCompilations(@PathVariable long compId) {
        log.info("Открепление подборки на главной странице.");
        compilationService.cancelPinEvent(compId);
    }
}
