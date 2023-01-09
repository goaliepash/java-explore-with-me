package ru.practicum.explorewithme.comment.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.comment.model.dto.CommentDto;
import ru.practicum.explorewithme.comment.model.dto.CommentMapper;
import ru.practicum.explorewithme.comment.repository.CommentRepository;
import ru.practicum.explorewithme.comment.service.CommentAdminService;
import ru.practicum.explorewithme.exception.user.UserNotFoundException;
import ru.practicum.explorewithme.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentAdminServiceImpl implements CommentAdminService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public List<CommentDto> get(long userId) {
        checkUserExists(userId);
        return commentRepository
                .findAllByAuthorId(userId)
                .stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void delete(long commentId) {
        commentRepository.deleteById(commentId);
    }

    private void checkUserExists(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(String.format("Пользователь с идентификатором %d не найден.", userId));
        }
    }
}