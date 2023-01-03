package ru.practicum.explorewithme.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.request.model.dto.RequestDto;
import ru.practicum.explorewithme.request.service.RequestPrivateService;

import java.util.List;

@RestController
@RequestMapping(value = "/users/{userId}/requests")
@Slf4j
@RequiredArgsConstructor
public class RequestPrivateController {

    private final RequestPrivateService requestPrivateService;

    @GetMapping
    public List<RequestDto> get(@PathVariable long userId) {
        log.info("Выполнен запрос GEt /users/{}/requests.", userId);
        return requestPrivateService.get(userId);
    }

    @PostMapping
    public RequestDto create(@PathVariable long userId, @RequestParam(name = "eventId") long eventId) {
        log.info("Выполнен запрос POST /users/{}/requests?eventId={}.", userId, eventId);
        return requestPrivateService.create(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancel(@PathVariable long userId, @PathVariable long requestId) {
        log.info("Выполнен запрос PATCH /users/{}/requests/{}/cancel", userId, requestId);
        return requestPrivateService.cancel(userId, requestId);
    }
}