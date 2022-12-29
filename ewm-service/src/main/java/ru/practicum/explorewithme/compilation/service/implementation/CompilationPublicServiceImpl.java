package ru.practicum.explorewithme.compilation.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.compilation.model.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.model.dto.CompilationMapper;
import ru.practicum.explorewithme.compilation.repository.CompilationRepository;
import ru.practicum.explorewithme.compilation.service.CompilationPublicService;
import ru.practicum.explorewithme.exception.compilation.CompilationNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationPublicServiceImpl implements CompilationPublicService {

    private final CompilationRepository compilationRepository;

    @Override
    public List<CompilationDto> get(Boolean pinned, int from, int size) {
        return compilationRepository
                .findAll(pinned, page(from, size))
                .stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto get(long compId) {
        checkIfCompilationExist(compId);
        return CompilationMapper.toCompilationDto(compilationRepository.getReferenceById(compId));
    }

    private static PageRequest page(int from, int size) {
        return PageRequest.of(from > 0 ? from / size : 0, size);
    }

    private void checkIfCompilationExist(long compId) {
        if (!compilationRepository.existsById(compId)) {
            throw new CompilationNotFoundException(String.format("Подборка с идентификатором %d не найдена.", compId));
        }
    }
}