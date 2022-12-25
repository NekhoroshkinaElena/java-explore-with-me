package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class PublicCategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<Category> getAll() {
        return categoryService.getALl();
    }

    @GetMapping("/{catId}")
    public Category getById(@PathVariable long catId) {
        return categoryService.getById(catId);
    }
}
