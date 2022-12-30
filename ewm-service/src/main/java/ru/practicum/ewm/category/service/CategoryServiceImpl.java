package ru.practicum.ewm.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.dto.CategoryDtoInput;
import ru.practicum.ewm.category.dto.CategoryDtoOutput;
import ru.practicum.ewm.category.dto.NewCategoryDto;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.AlreadyExistsException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.exception.ValidationException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public CategoryDtoOutput save(NewCategoryDto newCategoryDto) {
        try {
            return CategoryMapper.toCategoryDtoOutput(
                    categoryRepository.save(CategoryMapper.createCategory(newCategoryDto)));
        } catch (Exception ex) {
            log.error("Категория с таким названием уже существует.");
            throw new AlreadyExistsException("Категория с таким названием уже существует.");
        }
    }

    @Override
    public CategoryDtoOutput update(CategoryDtoInput categoryDtoInput) {
        Category categoryUpdate = getCategory(categoryDtoInput.getId());
        categoryUpdate.setName(categoryDtoInput.getName());
        try {
            return CategoryMapper.toCategoryDtoOutput(categoryRepository.save(categoryUpdate));
        } catch (Exception ex) {
            log.error("Категория с таким названием уже существует.");
            throw new AlreadyExistsException("Категория с таким названием уже существует.");
        }
    }

    @Override
    public void delete(long id) {
        if (eventRepository.findEventByCategoryId(id) != null) {
            log.error("С категорией не должно быть связано ни одного события.");
            throw new ValidationException("С категорией не должно быть связано ни одного события.");
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDtoOutput> getAll(int from, int size) {
        return categoryRepository.findAll(PageRequest.of(from, size)).stream()
                .map(CategoryMapper::toCategoryDtoOutput).collect(Collectors.toList());
    }

    @Override
    public CategoryDtoOutput getById(long id) {
        return CategoryMapper.toCategoryDtoOutput(getCategory(id));
    }

    private Category getCategory(long catId) {
        return categoryRepository.findById(catId).orElseThrow(() -> {
            log.error("Категория с id " + catId + " не найдена.");
            return new NotFoundException("Вещь с id " + catId + " не найдена.");
        });
    }
}
