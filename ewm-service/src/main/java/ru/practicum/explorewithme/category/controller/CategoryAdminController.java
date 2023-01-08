package ru.practicum.explorewithme.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.category.model.dto.CategoryDto;
import ru.practicum.explorewithme.category.service.CategoryAdminService;
import ru.practicum.explorewithme.constraint_group.Create;
import ru.practicum.explorewithme.constraint_group.Update;

@RestController
@RequestMapping(path = "/admin/categories")
@Slf4j
@RequiredArgsConstructor
public class CategoryAdminController {

    private final CategoryAdminService categoryAdminService;

    @PostMapping
    public CategoryDto create(@Validated(Create.class) @RequestBody CategoryDto categoryDto) {
        log.info("Выполнен запрос POST /admin/categories.");
        return categoryAdminService.create(categoryDto);
    }

    @PatchMapping
    public CategoryDto update(@Validated(Update.class) @RequestBody CategoryDto categoryDto) {
        log.info("Выполнен запрос PATCH /admin/categories.");
        return categoryAdminService.update(categoryDto);
    }

    @DeleteMapping("/{catId}")
    public void delete(@PathVariable long catId) {
        log.info("Выполнен запрос DELETE /admin/categories/{}.", catId);
        categoryAdminService.delete(catId);
    }
}