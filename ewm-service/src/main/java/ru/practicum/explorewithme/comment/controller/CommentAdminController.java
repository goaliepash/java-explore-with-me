package ru.practicum.explorewithme.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.comment.model.dto.CommentDto;
import ru.practicum.explorewithme.comment.service.CommentAdminService;

import java.util.List;

@RestController
@RequestMapping(path = "/admin/comments")
@Slf4j
@RequiredArgsConstructor
public class CommentAdminController {

    private final CommentAdminService commentAdminService;

    @GetMapping("/{userId}")
    public List<CommentDto> get(@PathVariable long userId) {
        log.info("Выполнен запрос GET /admin/comments/{}.", userId);
        return commentAdminService.get(userId);
    }

    @DeleteMapping("/{commentId}")
    public void delete(@PathVariable long commentId) {
        log.info("Выполнен запрос DELETE /admin/comments/{}.", commentId);
        commentAdminService.delete(commentId);
    }
}