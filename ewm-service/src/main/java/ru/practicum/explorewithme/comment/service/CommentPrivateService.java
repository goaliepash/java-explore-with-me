package ru.practicum.explorewithme.comment.service;

import ru.practicum.explorewithme.comment.model.dto.CommentDto;
import ru.practicum.explorewithme.comment.model.dto.CommentRequestDto;

public interface CommentPrivateService {

    CommentDto create(long userId, long eventId, CommentRequestDto commentRequestDto);

    void delete(long userId, long eventId, long commentId);
}