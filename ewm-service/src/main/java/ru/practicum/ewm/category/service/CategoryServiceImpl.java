package ru.practicum.ewm.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.model.CategoryDtoInput;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.exception.AlreadyExistsException;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        if (categoryRepository.findAllByName(category.getName()) != null) {
            log.error("такая категоря уже существует");
            throw new AlreadyExistsException("такая категоря уже существует");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category update(CategoryDtoInput categoryDtoInput) {
        Category category = categoryRepository.findById(categoryDtoInput.getId()).orElseThrow();
        if (categoryRepository.findAllByName(categoryDtoInput.getName()) != null) {
            log.error("такая категоря уже существует");
            throw new AlreadyExistsException("такая категоря уже существует");

        }
        category.setName(categoryDtoInput.getName());
        return category;
    }

    @Override
    public void delete(long id) {
        categoryRepository.delete(categoryRepository.findById(id).orElseThrow());
    }

    @Override
    public List<Category> getALl() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getById(long id) {
        return categoryRepository.findById(id).orElseThrow(() -> {
            log.error("Категория с id " + id + " не найдена.");
            return new NotFoundException("Вещь с id " + id + " не найдена.");
        });
    }
}
