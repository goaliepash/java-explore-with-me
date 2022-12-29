package ru.practicum.explorewithme.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.category.model.dto.CategoryDto;
import ru.practicum.explorewithme.category.service.CategoryPublicService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@Slf4j
@RequiredArgsConstructor
public class CategoryPublicController {

    private final CategoryPublicService service;

    @GetMapping
    public List<CategoryDto> get(
            @PositiveOrZero @RequestParam(name = "from", required = false, defaultValue = "0") int from,
            @Positive @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ) {
        log.info("Выполнен запрос GET /categories?from={}&size={}", from, size);
        return service.get(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getById(@PathVariable long catId) {
        log.info("Выполнен запрос GET /categories/{catId}.");
        return service.getById(catId);
    }
}