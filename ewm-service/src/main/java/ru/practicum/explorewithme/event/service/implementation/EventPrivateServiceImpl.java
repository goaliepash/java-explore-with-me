package ru.practicum.explorewithme.event.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.category.model.Category;
import ru.practicum.explorewithme.category.repository.CategoryRepository;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.model.EventState;
import ru.practicum.explorewithme.event.model.dto.EventDto;
import ru.practicum.explorewithme.event.model.dto.EventMapper;
import ru.practicum.explorewithme.event.model.dto.EventRequestDto;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.event.service.EventPrivateService;
import ru.practicum.explorewithme.exception.category.CategoryNotFoundException;
import ru.practicum.explorewithme.exception.event.EventBadRequestException;
import ru.practicum.explorewithme.exception.event.EventForbiddenException;
import ru.practicum.explorewithme.exception.event.EventNotFoundException;
import ru.practicum.explorewithme.exception.request.RequestNotFoundException;
import ru.practicum.explorewithme.exception.user.UserNotFoundException;
import ru.practicum.explorewithme.request.model.Request;
import ru.practicum.explorewithme.request.model.RequestStatus;
import ru.practicum.explorewithme.request.model.dto.RequestDto;
import ru.practicum.explorewithme.request.model.dto.RequestMapper;
import ru.practicum.explorewithme.request.repository.RequestRepository;
import ru.practicum.explorewithme.user.model.User;
import ru.practicum.explorewithme.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventPrivateServiceImpl implements EventPrivateService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;

    @Override
    public List<EventDto> get(long userId, int from, int size) {
        checkIfUserExists(userId);
        return eventRepository
                .findAll(userId, page(from, size))
                .stream()
                .map(EventMapper::toEventDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EventDto update(long userId, EventRequestDto eventRequestDto) {
        checkIfUserExists(userId);
        checkIfEventExists(eventRequestDto.getEventId());
        Event event = eventRepository.getReferenceById(eventRequestDto.getEventId());
        checkEventStateIsNotPublished(event);
        checkEventDate(event.getEventDate());
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
    public EventDto create(long userId, EventRequestDto eventRequestDto) {
        checkIfUserExists(userId);
        checkIfCategoryExists(eventRequestDto.getCategory());
        checkEventDate(LocalDateTime.parse(eventRequestDto.getEventDate(), FORMATTER));
        Event event = createEvent(userId, eventRequestDto);
        return EventMapper.toEventDto(eventRepository.save(event));
    }

    @Override
    public EventDto get(long userId, long eventId) {
        checkIfUserExists(userId);
        checkIfEventExists(eventId);
        return EventMapper.toEventDto(eventRepository.getReferenceById(eventId));
    }

    @Transactional
    @Override
    public EventDto cancel(long userId, long eventId) {
        checkIfUserExists(userId);
        checkIfEventExists(eventId);
        Event event = eventRepository.getReferenceById(eventId);
        checkIfEventPending(event);
        event.setState(EventState.CANCELED);
        return EventMapper.toEventDto(event);
    }

    @Override
    public List<RequestDto> getEventRequests(long userId, long eventId) {
        checkIfUserExists(userId);
        checkIfEventExists(eventId);
        return requestRepository.getEventRequests(eventId).stream().map(RequestMapper::toRequestDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public RequestDto confirmRequest(long userId, long eventId, long reqId) {
        checkIfUserExists(userId);
        checkIfEventExists(eventId);
        checkIfRequestExists(reqId);
        Event event = eventRepository.getReferenceById(eventId);
        Request request = requestRepository.getReferenceById(reqId);
        if (event.getRequestModeration() && event.getParticipantLimit() == 0) {
            return RequestMapper.toRequestDto(request);
        }
        if (Objects.equals(event.getConfirmedRequests(), event.getParticipantLimit())) {
            List<Request> requests = requestRepository.getEventRequestsByStatus(eventId, RequestStatus.PENDING);
            requests.forEach(currentRequest -> currentRequest.setStatus(RequestStatus.REJECTED));
            return RequestMapper.toRequestDto(request);
        }
        request.setStatus(RequestStatus.CONFIRMED);
        return RequestMapper.toRequestDto(request);
    }

    @Transactional
    @Override
    public RequestDto rejectRequest(long userId, long eventId, long reqId) {
        checkIfUserExists(userId);
        checkIfEventExists(eventId);
        checkIfRequestExists(reqId);
        Request request = requestRepository.getReferenceById(reqId);
        request.setStatus(RequestStatus.REJECTED);
        return RequestMapper.toRequestDto(request);
    }

    private void checkIfUserExists(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(String.format("Пользователь с идентификатором %d не найден.", userId));
        }
    }

    private void checkIfCategoryExists(long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException(String.format("Категория с идентификатором %d не найден.", categoryId));
        }
    }

    private Event createEvent(long userId, EventRequestDto eventRequestDto) {
        User initiator = userRepository.getReferenceById(userId);
        Category category = categoryRepository.getReferenceById(eventRequestDto.getCategory());
        Event event = EventMapper.fromEventRequestDto(eventRequestDto, category);
        event.setInitiator(initiator);
        event.setState(EventState.PENDING);
        event.setCreatedOn(LocalDateTime.now());
        return event;
    }

    private PageRequest page(int from, int size) {
        return PageRequest.of(from > 0 ? from / size : 0, size);
    }

    private void checkIfEventExists(long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new EventNotFoundException(String.format("Событие с идентификатором %d не найдено.", eventId));
        }
    }

    private void checkEventStateIsNotPublished(Event event) {
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new EventBadRequestException("Редактировать можно только события на модерации или отменённые.");
        }
    }

    private void checkEventDate(LocalDateTime eventTime) {
        LocalDateTime nowPlus2Hours = LocalDateTime.now().plusHours(2);
        if (nowPlus2Hours.isAfter(eventTime)) {
            throw new EventBadRequestException("Дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента.");
        }
    }

    private void checkIfEventPending(Event event) {
        if (!event.getState().equals(EventState.PENDING)) {
            throw new EventForbiddenException("Отменить можно только события в ожидании на модерации.");
        }
    }

    private void checkIfRequestExists(long reqId) {
        if (!requestRepository.existsById(reqId)) {
            throw new RequestNotFoundException(String.format("Заявка с идентификатором %d не найдена.", reqId));
        }
    }
}