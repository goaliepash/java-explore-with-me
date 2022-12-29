package ru.practicum.explorewithme.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.compilation.model.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.service.CompilationPublicService;

import java.util.List;

@RestController
@RequestMapping(path = "/compilations")
@Slf4j
@RequiredArgsConstructor
public class CompilationPublicController {

    private final CompilationPublicService service;

    @GetMapping
    public List<CompilationDto> get(
            @RequestParam(required = false) Boolean pinned,
            @RequestParam(required = false, defaultValue = "0") int from,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ) {
        log.info("Выполнен запрос GET /compilations?pinned={}&from={}&size={}.", pinned, from, size);
        return service.get(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto get(@PathVariable long compId) {
        log.info("Выполнен запрос GET /compilations/{}.", compId);
        return service.get(compId);
    }
}