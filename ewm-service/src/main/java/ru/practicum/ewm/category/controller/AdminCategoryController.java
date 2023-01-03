package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDtoInput;
import ru.practicum.ewm.category.dto.CategoryDtoOutput;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.service.CategoryService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
public class AdminCategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public CategoryDtoOutput save(@Valid @RequestBody NewCategoryDto categoryDto) {
        log.info("Добавление новой категории.");
        return categoryService.save(categoryDto);
    }

    @PatchMapping
    public CategoryDtoOutput update(@Valid @RequestBody CategoryDtoInput categoryDtoInput) {
        log.info("Изменение категории.");
        return categoryService.update(categoryDtoInput);
    }

    @DeleteMapping("/{catId}")
    public void delete(@PathVariable long catId) {
        log.info("Удаление категории.");
        categoryService.delete(catId);
    }
}
