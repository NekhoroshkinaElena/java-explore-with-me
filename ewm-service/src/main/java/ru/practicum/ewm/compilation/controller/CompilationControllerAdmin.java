package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.model.CompilationInputDto;
import ru.practicum.ewm.compilation.model.CompilationMapper;
import ru.practicum.ewm.compilation.model.CompilationOutputDto;
import ru.practicum.ewm.compilation.service.CompilationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class CompilationControllerAdmin {
    private final CompilationService compilationService;

    @PostMapping
    public CompilationOutputDto postCompilation(@Valid @RequestBody CompilationInputDto compilationInputDto) {
        return CompilationMapper.toCompilationOutputDto(compilationService.postCompilation(compilationInputDto));
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable long compId) {
        compilationService.deleteCompilation(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable long compId, @PathVariable long eventId) {
        //compilationService.deleteCompilation(compId);
        compilationService.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventInCompilation(@PathVariable long compId, @PathVariable long eventId) {
        compilationService.addEvent(compId, eventId);
    }

    @PatchMapping("/{compId}/pin")
    public void pinnedCompilation(@PathVariable long compId) {
        compilationService.pinEvent(compId);
    }

    @DeleteMapping("/{compId}/pin")
    public void cancelPinnedCompilation(@PathVariable long compId) {
        compilationService.cancelPinEvent(compId);
    }
}
