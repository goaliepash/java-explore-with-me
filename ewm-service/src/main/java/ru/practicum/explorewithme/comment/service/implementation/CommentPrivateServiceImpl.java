package ru.practicum.explorewithme.comment.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.comment.model.Comment;
import ru.practicum.explorewithme.comment.model.dto.CommentDto;
import ru.practicum.explorewithme.comment.model.dto.CommentMapper;
import ru.practicum.explorewithme.comment.model.dto.CommentRequestDto;
import ru.practicum.explorewithme.comment.repository.CommentRepository;
import ru.practicum.explorewithme.comment.service.CommentPrivateService;
import ru.practicum.explorewithme.event.model.Event;
import ru.practicum.explorewithme.event.repository.EventRepository;
import ru.practicum.explorewithme.exception.comment.CommentNotFoundException;
import ru.practicum.explorewithme.exception.event.EventNotFoundException;
import ru.practicum.explorewithme.exception.user.UserNotFoundException;
import ru.practicum.explorewithme.user.model.User;
import ru.practicum.explorewithme.user.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentPrivateServiceImpl implements CommentPrivateService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public CommentDto create(long userId, long eventId, CommentRequestDto commentRequestDto) {
        checkUserExists(userId);
        checkEventExists(eventId);
        User author = userRepository.getReferenceById(userId);
        Event event = eventRepository.getReferenceById(eventId);
        Comment comment = new Comment();
        comment.setText(commentRequestDto.getText());
        comment.setEvent(event);
        comment.setAuthor(author);
        comment.setCreated(LocalDateTime.now());
        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Transactional
    @Override
    public CommentDto update(long userId, long eventId, long commentId, CommentRequestDto commentRequestDto) {
        checkUserExists(userId);
        checkEventExists(eventId);
        checkCommentExists(commentId);
        Comment comment = commentRepository.getReferenceById(commentId);
        comment.setText(commentRequestDto.getText());
        return CommentMapper.toCommentDto(comment);
    }

    @Transactional
    @Override
    public void delete(long userId, long eventId, long commentId) {
        checkUserExists(userId);
        checkEventExists(eventId);
        commentRepository.deleteById(commentId);
    }

    private void checkUserExists(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(String.format("Пользователь с идентификатором %d не найден.", userId));
        }
    }

    private void checkEventExists(long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new EventNotFoundException(String.format("Событие с идентификатором %d не найден.", eventId));
        }
    }

    private void checkCommentExists(long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new CommentNotFoundException(String.format("Комментарий с идентификатором %d не найден.", commentId));
        }
    }
}