package ru.practicum.explorewithme.category.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.category.model.dto.CategoryDto;
import ru.practicum.explorewithme.category.model.dto.CategoryMapper;
import ru.practicum.explorewithme.category.repository.CategoryRepository;
import ru.practicum.explorewithme.category.service.CategoryAdminService;
import ru.practicum.explorewithme.exception.category.CategoryConflictException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryAdminServiceImpl implements CategoryAdminService {

    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        checkIfCategoryNameExists(categoryDto.getName());
        Category category = CategoryMapper.fromCategoryDto(categoryDto);
        return CategoryMapper.toCategoryDto(categoryRepository.save(category));
    }

    @Transactional
    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        checkIfCategoryNameExists(categoryDto.getName());
        Category category = categoryRepository.getReferenceById(categoryDto.getId());
        category.setName(categoryDto.getName());
        return CategoryMapper.toCategoryDto(category);
    }

    @Transactional
    @Override
    public void delete(long catId) {
        categoryRepository.deleteById(catId);
    }

    private void checkIfCategoryNameExists(String name) {
        if (!categoryRepository.findAllByName(name).isEmpty()) {
            throw new CategoryConflictException("Имя категории должно быть уникальным.");
        }
    }
}