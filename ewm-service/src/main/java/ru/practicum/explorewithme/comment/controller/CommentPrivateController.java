package ru.practicum.explorewithme.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.comment.model.dto.CommentDto;
import ru.practicum.explorewithme.comment.model.dto.CommentRequestDto;
import ru.practicum.explorewithme.comment.service.CommentPrivateService;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping(path = "/users/{userId}/events/{eventId}/comments")
@Slf4j
@RequiredArgsConstructor
@Validated
public class CommentPrivateController {

    private final CommentPrivateService commentPrivateService;

    @PostMapping
    public CommentDto create(
            @PathVariable @Positive long userId,
            @PathVariable @Positive long eventId,
            @RequestBody CommentRequestDto commentRequestDto
    ) {
        log.info("Выполнен запрос POST /users/{}/events/{}/comments.", userId, eventId);
        return commentPrivateService.create(userId, eventId, commentRequestDto);
    }

    @PatchMapping("/{commentId}")
    public CommentDto update(
            @PathVariable @Positive long userId,
            @PathVariable @Positive long eventId,
            @PathVariable @Positive long commentId,
            @RequestBody CommentRequestDto commentRequestDto
    ) {
        log.info("Выполнен запрос PATCH /users/{}/events/{}/comments/{}.", userId, eventId, commentId);
        return commentPrivateService.update(userId, eventId, commentId, commentRequestDto);
    }

    @DeleteMapping("/{commentId}")
    public void delete(
            @PathVariable @Positive long userId,
            @PathVariable @Positive long eventId,
            @PathVariable @Positive long commentId) {
        log.info("Выполнен запрос DELETE /users/{}/events/{}/comments/{}.", userId, eventId, commentId);
        commentPrivateService.delete(userId, eventId, commentId);
    }
}