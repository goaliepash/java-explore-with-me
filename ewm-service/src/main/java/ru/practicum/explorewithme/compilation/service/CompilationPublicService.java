package ru.practicum.explorewithme.compilation.service;

import ru.practicum.explorewithme.compilation.model.dto.CompilationDto;

import java.util.List;

public interface CompilationPublicService {

    List<CompilationDto> get(Boolean pinned, int from, int size);

    CompilationDto get(long compId);
}