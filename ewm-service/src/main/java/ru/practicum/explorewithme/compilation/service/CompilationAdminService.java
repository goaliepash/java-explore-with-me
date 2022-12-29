package ru.practicum.explorewithme.compilation.service;

import ru.practicum.explorewithme.compilation.model.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.model.dto.CompilationRequestDto;

public interface CompilationAdminService {

    CompilationDto create(CompilationRequestDto compilationRequestDto);

    void delete(long compId);

    void delete(long compId, long eventId);

    void addEvent(long compId, long eventId);

    void unpin(long compId);

    void pin(long compId);
}