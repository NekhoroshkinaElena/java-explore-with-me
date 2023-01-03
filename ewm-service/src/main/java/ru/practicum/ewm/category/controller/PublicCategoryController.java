package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDtoOutput;
import ru.practicum.ewm.category.service.CategoryService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class PublicCategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDtoOutput> getAll(@RequestParam(value = "from", required = false, defaultValue = "0") int from,
                                          @RequestParam(value = "size", required = false, defaultValue = "10")
                                          int size) {
        log.info("Получение категорий.");
        return categoryService.getAll(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDtoOutput getById(@PathVariable long catId) {
        log.info("Получение категории по её идентификатору.");
        return categoryService.getById(catId);
    }
}
