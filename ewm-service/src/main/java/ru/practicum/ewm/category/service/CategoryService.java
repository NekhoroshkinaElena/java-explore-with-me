package ru.practicum.ewm.category.service;

import ru.practicum.ewm.category.dto.CategoryDtoInput;
import ru.practicum.ewm.category.dto.CategoryDtoOutput;
import ru.practicum.ewm.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDtoOutput save(NewCategoryDto category);

    CategoryDtoOutput update(CategoryDtoInput categoryDtoInput);

    void delete(long id);

    List<CategoryDtoOutput> getAll(int from, int size);

    CategoryDtoOutput getById(long id);
}
