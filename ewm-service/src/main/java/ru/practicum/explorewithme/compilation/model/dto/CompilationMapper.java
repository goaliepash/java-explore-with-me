package ru.practicum.explorewithme.compilation.model.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.compilation.model.Compilation;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.model.dto.EventMapper;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CompilationMapper {

    public static Compilation fromCompilationRequestDto(CompilationRequestDto compilationRequestDto, List<Event> compilationEvents) {
        Compilation compilation = new Compilation();
        compilation.setId(compilationRequestDto.getId());
        compilation.setPinned(compilationRequestDto.getPinned());
        compilation.setTitle(compilationRequestDto.getTitle());
        compilation.setEvents(compilationEvents);
        return compilation;
    }

    public static Compilation fromCompilationDto(CompilationDto compilationDto) {
        Compilation compilation = new Compilation();
        compilation.setId(compilationDto.getId());
        compilation.setPinned(compilationDto.getPinned());
        compilation.setTitle(compilationDto.getTitle());
        compilation.setEvents(compilationDto.getEvents().stream().map(EventMapper::fromEventDto).collect(Collectors.toList()));
        return compilation;
    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
        CompilationDto compilationDto = new CompilationDto();
        compilationDto.setId(compilation.getId());
        compilationDto.setPinned(compilation.getPinned());
        compilationDto.setTitle(compilation.getTitle());
        compilationDto.setEvents(compilation.getEvents().stream().map(EventMapper::toEventDto).collect(Collectors.toList()));
        return compilationDto;
    }
}