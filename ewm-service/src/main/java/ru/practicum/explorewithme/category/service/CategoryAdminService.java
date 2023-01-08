package ru.practicum.explorewithme.category.service;

import ru.practicum.explorewithme.category.model.dto.CategoryDto;

public interface CategoryAdminService {

    CategoryDto create(CategoryDto categoryDto);

    CategoryDto update(CategoryDto categoryDto);

    void delete(long catId);
}