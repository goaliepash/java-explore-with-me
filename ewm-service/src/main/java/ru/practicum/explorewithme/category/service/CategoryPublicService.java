package ru.practicum.explorewithme.category.service;

import ru.practicum.explorewithme.category.model.dto.CategoryDto;

import java.util.List;

public interface CategoryPublicService {

    List<CategoryDto> get(int from, int size);

    CategoryDto getById(long catId);
}