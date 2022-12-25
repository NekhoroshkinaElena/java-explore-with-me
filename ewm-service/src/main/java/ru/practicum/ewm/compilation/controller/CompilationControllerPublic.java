package ru.practicum.ewm.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.service.CompilationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class CompilationControllerPublic {
    private final CompilationService compilationService;

    @GetMapping("/{compId}")
    public Compilation getById(@PathVariable long compId) {
        return compilationService.getById(compId);
    }

    @GetMapping
    public List<Compilation> getAll(@RequestParam(value = "pinned", required = false, defaultValue = "false") Boolean pinned,
                                    @RequestParam(value = "from", required = false, defaultValue = "0") int from,
                                    @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return compilationService.getAll(pinned, from, size);
    }
}
