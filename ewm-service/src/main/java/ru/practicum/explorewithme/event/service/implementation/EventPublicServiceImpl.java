package ru.practicum.explorewithme.event.service.implementation;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.client.event.EventClient;
import ru.practicum.explorewithme.event.model.EventSort;
import ru.practicum.explorewithme.event.model.EventState;
import ru.practicum.explorewithme.event.model.dto.EndPointHitDto;
import ru.practicum.explorewithme.event.model.dto.EventDto;
import ru.practicum.explorewithme.event.model.dto.EventMapper;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.event.service.EventPublicService;
import ru.practicum.explorewithme.exception.event.EventBadRequestException;
import ru.practicum.explorewithme.exception.event.EventNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventPublicServiceImpl implements EventPublicService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final EventClient eventClient;
    private final EventRepository eventRepository;

    @Override
    public List<EventDto> get(
            String text,
            List<Long> categories,
            Boolean paid,
            String rangeStart,
            String rangeEnd,
            Boolean onlyAvailable,
            String sort,
            int from,
            int size,
            HttpServletRequest request
    ) {
        PageRequest pageRequest = getPageRequest(from, size, sort);
        checkDateTimePeriod(rangeStart, rangeEnd);
        LocalDateTime start = rangeStart != null ? LocalDateTime.parse(rangeStart, FORMATTER) : null;
        LocalDateTime end = rangeStart != null ? LocalDateTime.parse(rangeEnd, FORMATTER) : null;
        EndPointHitDto endPointHitDto = EndPointHitDto
                .builder()
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI())
                .app("ewm-main-service")
                .build();
        eventClient.addHit(endPointHitDto);
        return eventRepository
                .findAll(EventState.PUBLISHED, text, categories, paid, start, end, onlyAvailable, pageRequest)
                .stream()
                .map(EventMapper::toEventDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventDto get(long eventId, HttpServletRequest request) {
        checkIfEventExists(eventId);
        EndPointHitDto endPointHitDto = EndPointHitDto
                .builder()
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI())
                .app("ewm-main-service")
                .build();
        eventClient.addHit(endPointHitDto);
        return EventMapper.toEventDto(eventRepository.getReferenceById(eventId));
    }

    private static PageRequest getPageRequest(int from, int size, String sort) {
        if (sort != null) {
            if (!EnumUtils.isValidEnum(EventSort.class, sort)) {
                throw new EventBadRequestException(String.format("Указан неверный вариант сортировки: %s.", sort));
            }
            if (sort.equals(EventSort.EVENT_DATE.name())) {
                return page(from, size, Sort.by(Sort.Direction.DESC, "eventDate"));
            } else {
                return page(from, size, Sort.by(Sort.Direction.DESC, "views"));
            }
        } else {
            return page(from, size);
        }
    }

    private static PageRequest page(int from, int size) {
        return PageRequest.of(from > 0 ? from / size : 0, size);
    }

    private static PageRequest page(int from, int size, Sort sort) {
        return PageRequest.of(from > 0 ? from / size : 0, size, sort);
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
}