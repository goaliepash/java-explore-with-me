package ru.practicum.explorewithme.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.compilation.model.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.model.dto.CompilationRequestDto;
import ru.practicum.explorewithme.compilation.service.CompilationAdminService;
import ru.practicum.explorewithme.constraint_group.Create;

@RestController
@RequestMapping(path = "/admin/compilations")
@Slf4j
@RequiredArgsConstructor
public class CompilationAdminController {

    private final CompilationAdminService service;

    @PostMapping
    public CompilationDto create(@Validated(Create.class) @RequestBody CompilationRequestDto compilationRequestDto) {
        log.info("Выполнен запрос POST /admin/compilations.");
        return service.create(compilationRequestDto);
    }

    @DeleteMapping("/{compId}")
    public void delete(@PathVariable long compId) {
        log.info("Выполнен запрос DELETE /admin/compilations/{}.", compId);
        service.delete(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void delete(@PathVariable long compId, @PathVariable long eventId) {
        log.info("Выполнен запрос DELETE /admin/compilations/{}/events/{}.", compId, eventId);
        service.delete(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEvent(@PathVariable long compId, @PathVariable long eventId) {
        log.info("Выполнен запрос PATCH /admin/compilations/{}/events/{}.", compId, eventId);
        service.addEvent(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unpin(@PathVariable long compId) {
        log.info("Выполнен запрос DELETE /admin/compilations/{}/pin.", compId);
        service.unpin(compId);
    }

    @PatchMapping("/{compId}/pin")
    public void pin(@PathVariable long compId) {
        log.info("Выполнен запрос PATCH /admin/compilations/{}/pin.", compId);
        service.pin(compId);
    }
}