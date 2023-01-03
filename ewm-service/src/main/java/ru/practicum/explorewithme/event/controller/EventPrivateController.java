package ru.practicum.explorewithme.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.constraint_group.Create;
import ru.practicum.explorewithme.constraint_group.Update;
import ru.practicum.explorewithme.event.model.dto.EventDto;
import ru.practicum.explorewithme.event.model.dto.EventRequestDto;
import ru.practicum.explorewithme.event.service.EventPrivateService;
import ru.practicum.explorewithme.request.model.dto.RequestDto;

import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/events")
@Slf4j
@RequiredArgsConstructor
public class EventPrivateController {

    private final EventPrivateService eventPrivateService;

    @GetMapping
    public List<EventDto> get(
            @PathVariable long userId,
            @RequestParam(name = "from", required = false, defaultValue = "0") int from,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ) {
        log.info("Выполнен запрос GET /users/{}/events?from={}&size={}.", userId, from, size);
        return eventPrivateService.get(userId, from, size);
    }

    @PatchMapping
    public EventDto update(@PathVariable long userId, @Validated(Update.class) @RequestBody EventRequestDto eventRequestDto) {
        log.info("Выполнен запрос PATCH /users/{}/events.", userId);
        return eventPrivateService.update(userId, eventRequestDto);
    }

    @PostMapping
    public EventDto create(@PathVariable long userId, @Validated(Create.class) @RequestBody EventRequestDto eventRequestDto) {
        log.info("Выполнен запрос POST /users/{}/events.", userId);
        return eventPrivateService.create(userId, eventRequestDto);
    }

    @GetMapping("/{eventId}")
    public EventDto get(@PathVariable long userId, @PathVariable long eventId) {
        log.info("Выполнен запрос GET /users/{}/events/{}.", userId, eventId);
        return eventPrivateService.get(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventDto cancel(@PathVariable long userId, @PathVariable long eventId) {
        log.info("Выполнен запрос PATCH /users/{}/events/{}.", userId, eventId);
        return eventPrivateService.cancel(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    public List<RequestDto> getEventRequests(@PathVariable long userId, @PathVariable long eventId) {
        log.info("Выполнен запрос GET /users/{}/events/{}/requests.", userId, eventId);
        return eventPrivateService.getEventRequests(userId, eventId);
    }

    @PatchMapping("{eventId}/requests/{reqId}/confirm")
    public RequestDto confirmRequest(@PathVariable long userId, @PathVariable long eventId, @PathVariable long reqId) {
        log.info("Выполнен запрос PATCH /users/{}/events/{}/requests/{}/confirm.", userId, eventId, reqId);
        return eventPrivateService.confirmRequest(userId, eventId, reqId);
    }

    @PatchMapping("{eventId}/requests/{reqId}/reject")
    public RequestDto rejectRequest(@PathVariable long userId, @PathVariable long eventId, @PathVariable long reqId) {
        log.info("Выполнен запрос PATCH /users/{}/events/{}/requests/{}/reject.", userId, eventId, reqId);
        return eventPrivateService.rejectRequest(userId, eventId, reqId);
    }
}