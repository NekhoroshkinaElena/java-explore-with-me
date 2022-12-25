package ru.practicum.ewm.category.service;

import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.model.CategoryDtoInput;

import java.util.List;

public interface CategoryService {

    Category save(Category category);

    Category update(CategoryDtoInput categoryDtoInput);

    void delete(long id);

    List<Category> getALl();

    Category getById(long id);
}
