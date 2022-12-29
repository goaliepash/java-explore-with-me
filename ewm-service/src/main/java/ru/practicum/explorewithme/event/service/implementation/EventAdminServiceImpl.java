package ru.practicum.explorewithme.event.service.implementation;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.category.repository.CategoryRepository;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.model.EventState;
import ru.practicum.explorewithme.event.model.dto.EventDto;
import ru.practicum.explorewithme.event.model.dto.EventMapper;
import ru.practicum.explorewithme.event.model.dto.EventRequestDto;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.event.service.EventAdminService;
import ru.practicum.explorewithme.exception.event.EventBadRequestException;
import ru.practicum.explorewithme.exception.event.EventForbiddenException;
import ru.practicum.explorewithme.exception.event.EventNotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventAdminServiceImpl implements EventAdminService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<EventDto> get(
            List<Long> users,
            List<String> states,
            List<Long> categories,
            String rangeStart,
            String rangeEnd,
            int from,
            int size
    ) {
        List<EventState> eventStates = new ArrayList<>();
        if (states != null) {
            states.forEach(state -> {
                if (EnumUtils.isValidEnumIgnoreCase(EventState.class, state)) {
                    eventStates.add(EnumUtils.getEnumIgnoreCase(EventState.class, state));
                } else {
                    throw new EventBadRequestException(String.format("Состояния %s для событий не существует.", state));
                }
            });
        }
        checkDateTimePeriod(rangeStart, rangeEnd);
        LocalDateTime start = rangeStart != null ? LocalDateTime.parse(rangeStart, FORMATTER) : null;
        LocalDateTime end = rangeEnd != null ? LocalDateTime.parse(rangeEnd, FORMATTER) : null;
        PageRequest pageRequest = page(from, size);
        return eventRepository.findAll(users, eventStates.isEmpty() ? null : eventStates, categories, start, end, pageRequest)
                .stream()
                .map(EventMapper::toEventDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EventDto update(long eventId, EventRequestDto eventRequestDto) {
        checkIfEventExists(eventId);
        Event event = eventRepository.getReferenceById(eventId);
        if (eventRequestDto.getAnnotation() != null) {
            event.setAnnotation(eventRequestDto.getAnnotation());
        }
        if (eventRequestDto.getCategory() != null) {
            event.setCategory(categoryRepository.getReferenceById(eventRequestDto.getCategory()));
        }
        if (eventRequestDto.getDescription() != null) {
            event.setDescription(eventRequestDto.getDescription());
        }
        if (eventRequestDto.getEventDate() != null) {
            LocalDateTime eventDate = LocalDateTime.parse(eventRequestDto.getEventDate(), FORMATTER);
            event.setEventDate(eventDate);
        }
        if (eventRequestDto.getPaid() != null) {
            event.setPaid(eventRequestDto.getPaid());
        }
        if (eventRequestDto.getParticipantLimit() != null) {
            event.setParticipantLimit(eventRequestDto.getParticipantLimit());
        }
        if (eventRequestDto.getTitle() != null) {
            event.setTitle(eventRequestDto.getTitle());
        }
        return EventMapper.toEventDto(event);
    }

    @Transactional
    @Override
    public EventDto publish(long eventId) {
        checkIfEventExists(eventId);
        Event event = eventRepository.getReferenceById(eventId);
        checkIfEventNotPending(event);
        checkPublishEventDate(event);
        event.setState(EventState.PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());
        return EventMapper.toEventDto(event);
    }

    @Transactional
    @Override
    public EventDto reject(long eventId) {
        checkIfEventExists(eventId);
        Event event = eventRepository.getReferenceById(eventId);
        checkIfEventNotPublished(event);
        event.setState(EventState.CANCELED);
        return EventMapper.toEventDto(event);
    }

    private PageRequest page(int from, int size) {
        return PageRequest.of(from > 0 ? from / size : 0, size);
    }

    private void checkDateTimePeriod(String rangeStart, String rangeEnd) {
        if (rangeStart != null && rangeEnd != null) {
            LocalDateTime start = LocalDateTime.parse(rangeStart, FORMATTER);
            LocalDateTime end = LocalDateTime.parse(rangeEnd, FORMATTER);
            if (start.isAfter(end)) {
                throw new EventBadRequestException("Левая граница временного периода не может быть позже правой.");
            }
        }
    }

    private void checkIfEventExists(long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new EventNotFoundException(String.format("Событие с идентификатором %d не найдено.", eventId));
        }
    }

    private void checkIfEventNotPending(Event event) {
        if (!event.getState().equals(EventState.PENDING)) {
            throw new EventForbiddenException("Для публикации события оно должно быть в состоянии модерации.");
        }
    }

    private void checkPublishEventDate(Event event) {
        LocalDateTime nowPlusOneHour = LocalDateTime.now().plusHours(1);
        if (nowPlusOneHour.isAfter(event.getEventDate())) {
            throw new EventForbiddenException("Дата начала события должна быть не ранее чем за час от даты публикации.");
        }
    }

    private void checkIfEventNotPublished(Event event) {
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new EventForbiddenException("Событие не должно быть опубликовано.");
        }
    }
}