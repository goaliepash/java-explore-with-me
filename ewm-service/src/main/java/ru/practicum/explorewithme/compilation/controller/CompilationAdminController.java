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

    private final CompilationAdminService compilationAdminService;

    @PostMapping
    public CompilationDto create(@Validated(Create.class) @RequestBody CompilationRequestDto compilationRequestDto) {
        log.info("Выполнен запрос POST /admin/compilations.");
        return compilationAdminService.create(compilationRequestDto);
    }

    @DeleteMapping("/{compId}")
    public void delete(@PathVariable long compId) {
        log.info("Выполнен запрос DELETE /admin/compilations/{}.", compId);
        compilationAdminService.delete(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void delete(@PathVariable long compId, @PathVariable long eventId) {
        log.info("Выполнен запрос DELETE /admin/compilations/{}/events/{}.", compId, eventId);
        compilationAdminService.delete(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEvent(@PathVariable long compId, @PathVariable long eventId) {
        log.info("Выполнен запрос PATCH /admin/compilations/{}/events/{}.", compId, eventId);
        compilationAdminService.addEvent(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unpin(@PathVariable long compId) {
        log.info("Выполнен запрос DELETE /admin/compilations/{}/pin.", compId);
        compilationAdminService.unpin(compId);
    }

    @PatchMapping("/{compId}/pin")
    public void pin(@PathVariable long compId) {
        log.info("Выполнен запрос PATCH /admin/compilations/{}/pin.", compId);
        compilationAdminService.pin(compId);
    }
}