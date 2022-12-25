package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.model.CategoryDtoInput;
import ru.practicum.ewm.category.model.CategoryMapper;
import ru.practicum.ewm.category.model.NewCategoryDto;
import ru.practicum.ewm.category.service.CategoryService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public Category save(@Valid @RequestBody NewCategoryDto categoryDto) {
        return categoryService.save(CategoryMapper.toCategory(categoryDto));
    }

    @PatchMapping
    public Category update(@Valid @RequestBody CategoryDtoInput categoryDtoInput) {
        return categoryService.update(categoryDtoInput);
    }

    @DeleteMapping("/{catId}")
    public void delete(@PathVariable long catId) {
        categoryService.delete(catId);
    }
}
