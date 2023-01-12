package ru.practicum.explorewithme.comment.service;

import ru.practicum.explorewithme.comment.model.dto.CommentDto;

import java.util.List;

public interface CommentAdminService {

    List<CommentDto> get(long userId);

    void delete(long commentId);
}