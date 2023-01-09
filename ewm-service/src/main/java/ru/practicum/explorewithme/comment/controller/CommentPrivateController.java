package ru.practicum.explorewithme.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.comment.model.dto.CommentDto;
import ru.practicum.explorewithme.comment.model.dto.CommentRequestDto;
import ru.practicum.explorewithme.comment.service.CommentPrivateService;

@RestController
@RequestMapping(path = "/users/{userId}/events/{eventId}/comments")
@Slf4j
@RequiredArgsConstructor
public class CommentPrivateController {

    private final CommentPrivateService commentPrivateService;

    @PostMapping
    public CommentDto create(
            @PathVariable long userId,
            @PathVariable long eventId,
            @RequestBody CommentRequestDto commentRequestDto
    ) {
        log.info("Выполнен запрос POST /users/{}/events/{}/comments.", userId, eventId);
        return commentPrivateService.create(userId, eventId, commentRequestDto);
    }

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable long userId, @PathVariable long eventId, @PathVariable long commentId) {
        log.info("Выполнен запрос DELETE /users/{}/events/{}/comments/{}.", userId, eventId, commentId);
        commentPrivateService.delete(userId, eventId, commentId);
    }
}