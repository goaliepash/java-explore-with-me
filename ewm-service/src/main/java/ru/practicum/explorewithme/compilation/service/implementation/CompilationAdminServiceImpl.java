package ru.practicum.explorewithme.compilation.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.compilation.model.Compilation;
import ru.practicum.explorewithme.compilation.model.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.model.dto.CompilationMapper;
import ru.practicum.explorewithme.compilation.model.dto.CompilationRequestDto;
import ru.practicum.explorewithme.compilation.repository.CompilationRepository;
import ru.practicum.explorewithme.compilation.service.CompilationAdminService;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.exception.compilation.CompilationBadRequestException;
import ru.practicum.explorewithme.exception.compilation.CompilationNotFoundException;
import ru.practicum.explorewithme.exception.event.EventNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationAdminServiceImpl implements CompilationAdminService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public CompilationDto create(CompilationRequestDto compilationRequestDto) {
        List<Event> compilationEvents = eventRepository.findAllById(compilationRequestDto.getEvents());
        Compilation compilation = CompilationMapper.fromCompilationRequestDto(compilationRequestDto, compilationEvents);
        return CompilationMapper.toCompilationDto(compilationRepository.save(compilation));
    }

    @Transactional
    @Override
    public void delete(long compId) {
        compilationRepository.deleteById(compId);
    }

    @Transactional
    @Override
    public void delete(long compId, long eventId) {
        checkIfCompilationExist(compId);
        checkIfEventExist(eventId);
        Compilation compilation = compilationRepository.getReferenceById(compId);
        checkIfCompilationContainsEvent(compilation, eventId);
        List<Event> events = compilation
                .getEvents()
                .stream()
                .filter(event -> event.getId() != eventId)
                .collect(Collectors.toList());
        compilation.setEvents(events);
        compilationRepository.save(compilation);
    }

    @Transactional
    @Override
    public void addEvent(long compId, long eventId) {
        checkIfCompilationExist(compId);
        checkIfEventExist(eventId);
        Compilation compilation = compilationRepository.getReferenceById(compId);
        Event event = eventRepository.getReferenceById(eventId);
        compilation.getEvents().add(event);
    }

    @Transactional
    @Override
    public void unpin(long compId) {
        checkIfCompilationExist(compId);
        Compilation compilation = compilationRepository.getReferenceById(compId);
        compilation.setPinned(false);
    }

    @Transactional
    @Override
    public void pin(long compId) {
        checkIfCompilationExist(compId);
        Compilation compilation = compilationRepository.getReferenceById(compId);
        compilation.setPinned(true);
    }

    private void checkIfCompilationExist(long compId) {
        if (!compilationRepository.existsById(compId)) {
            throw new CompilationNotFoundException(String.format("Подборка с идентификатором %d не найдена.", compId));
        }
    }

    private void checkIfEventExist(long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new EventNotFoundException(String.format("Событие с идентификатором %d не найдено.", eventId));
        }
    }

    private void checkIfCompilationContainsEvent(Compilation compilation, long eventId) {
        if (compilation.getEvents().stream().noneMatch(event -> event.getId() == eventId)) {
            throw new CompilationBadRequestException(String.format("События с идентификатором %d не найдено в подборке.", eventId));
        }
    }
}