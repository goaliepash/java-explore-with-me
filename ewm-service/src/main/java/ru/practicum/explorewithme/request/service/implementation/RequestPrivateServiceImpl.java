package ru.practicum.explorewithme.request.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.exception.event.EventNotFoundException;
import ru.practicum.explorewithme.exception.request.RequestNotFoundException;
import ru.practicum.explorewithme.exception.user.UserNotFoundException;
import ru.practicum.explorewithme.request.model.Request;
import ru.practicum.explorewithme.request.model.RequestStatus;
import ru.practicum.explorewithme.request.model.dto.RequestDto;
import ru.practicum.explorewithme.request.model.dto.RequestMapper;
import ru.practicum.explorewithme.request.repository.RequestRepository;
import ru.practicum.explorewithme.request.service.RequestPrivateService;
import ru.practicum.explorewithme.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestPrivateServiceImpl implements RequestPrivateService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public List<RequestDto> get(long userId) {
        checkIfUserExists(userId);
        return requestRepository.findAllByRequesterId(userId).stream().map(RequestMapper::toRequestDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public RequestDto create(long userId, long eventId) {
        // Проверка
        checkIfUserExists(userId);
        checkIfEventExists(eventId);
        // Подготовка
        Request request = createRequest(userId, eventId);
        // Исполнение
        Request newRequest = requestRepository.save(request);
        return RequestMapper.toRequestDto(newRequest);
    }

    @Override
    public RequestDto cancel(long userId, long requestId) {
        checkIfUserExists(userId);
        checkIfRequestExist(requestId);
        Request request = requestRepository.getReferenceById(requestId);
        request.setStatus(RequestStatus.CANCELED);
        return RequestMapper.toRequestDto(request);
    }

    private Request createRequest(long userId, long eventId) {
        Request request = new Request();
        request.setCreated(LocalDateTime.now());
        request.setEvent(eventRepository.getReferenceById(eventId));
        request.setRequester(userRepository.getReferenceById(userId));
        request.setStatus(RequestStatus.PENDING);
        return request;
    }

    private void checkIfUserExists(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(String.format("Пользователь с идентификатором %d не найден.", userId));
        }
    }

    private void checkIfEventExists(long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new EventNotFoundException(String.format("Событие с идентификатором %d не найдено.", eventId));
        }
    }

    private void checkIfRequestExist(long requestId) {
        if (!requestRepository.existsById(requestId)) {
            throw new RequestNotFoundException(String.format("Заявка с идентификатором %d не найдена.", requestId));
        }
    }
}