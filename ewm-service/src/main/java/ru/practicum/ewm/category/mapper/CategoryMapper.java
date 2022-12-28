package ru.practicum.ewm.category.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.category.dto.CategoryDtoOutput;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.model.Category;

@UtilityClass
public class CategoryMapper {

    public static Category toCategory(NewCategoryDto newCategoryDto) {
        return new Category(0L, newCategoryDto.getName());
    }

    public static CategoryDtoOutput toCategoryDtoOutput(Category category) {
        return new CategoryDtoOutput(category.getId(), category.getName());
    }
}
