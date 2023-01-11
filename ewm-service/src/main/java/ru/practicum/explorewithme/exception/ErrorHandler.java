package ru.practicum.explorewithme.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.explorewithme.category.controller.CategoryAdminController;
import ru.practicum.explorewithme.category.controller.CategoryPublicController;
import ru.practicum.explorewithme.compilation.controller.CompilationPublicController;
import ru.practicum.explorewithme.event.controller.EventAdminController;
import ru.practicum.explorewithme.event.controller.EventPrivateController;
import ru.practicum.explorewithme.exception.category.CategoryConflictException;
import ru.practicum.explorewithme.exception.category.CategoryNotFoundException;
import ru.practicum.explorewithme.exception.comment.CommentNotFoundException;
import ru.practicum.explorewithme.exception.compilation.CompilationBadRequestException;
import ru.practicum.explorewithme.exception.compilation.CompilationNotFoundException;
import ru.practicum.explorewithme.exception.event.EventBadRequestException;
import ru.practicum.explorewithme.exception.event.EventForbiddenException;
import ru.practicum.explorewithme.exception.event.EventNotFoundException;
import ru.practicum.explorewithme.exception.event.EventStateNotFoundException;
import ru.practicum.explorewithme.exception.request.RequestNotFoundException;
import ru.practicum.explorewithme.exception.user.UserConflictException;
import ru.practicum.explorewithme.exception.user.UserNotFoundException;
import ru.practicum.explorewithme.request.controller.RequestPrivateController;
import ru.practicum.explorewithme.user.controller.UserAdminController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestControllerAdvice(assignableTypes = {
        CategoryAdminController.class,
        CategoryPublicController.class,
        UserAdminController.class,
        EventAdminController.class,
        EventPrivateController.class,
        EventAdminController.class,
        RequestPrivateController.class,
        CompilationPublicController.class
})
public class ErrorHandler {

    @ExceptionHandler
    private ResponseEntity<Map<String, String>> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(
                Map.of(
                        "message", Objects.requireNonNull(exception.getMessage()),
                        "status", HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        "timestamp", LocalDateTime.now().toString()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    private ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(
                Map.of(
                        "message", exception.getMessage(),
                        "status", HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        "timestamp", LocalDateTime.now().toString()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleEventBadRequestException(EventBadRequestException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(
                Map.of(
                        "message", exception.getMessage(),
                        "status", HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        "timestamp", LocalDateTime.now().toString()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleCompilationBadRequestException(CompilationBadRequestException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(
                Map.of(
                        "message", exception.getMessage(),
                        "status", HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        "timestamp", LocalDateTime.now().toString()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleEventForbiddenException(EventForbiddenException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(
                Map.of(
                        "message", exception.getMessage(),
                        "status", HttpStatus.FORBIDDEN.getReasonPhrase(),
                        "timestamp", LocalDateTime.now().toString()),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleCategoryNotFoundException(CategoryNotFoundException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(
                Map.of(
                        "message", exception.getMessage(),
                        "status", HttpStatus.NOT_FOUND.getReasonPhrase(),
                        "timestamp", LocalDateTime.now().toString()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(UserNotFoundException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(
                Map.of(
                        "message", exception.getMessage(),
                        "status", HttpStatus.NOT_FOUND.getReasonPhrase(),
                        "timestamp", LocalDateTime.now().toString()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleEventNotFoundException(EventNotFoundException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(
                Map.of(
                        "message", exception.getMessage(),
                        "status", HttpStatus.NOT_FOUND.getReasonPhrase(),
                        "timestamp", LocalDateTime.now().toString()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleEventStateNotFoundException(EventStateNotFoundException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(
                Map.of(
                        "message", exception.getMessage(),
                        "status", HttpStatus.NOT_FOUND.getReasonPhrase(),
                        "timestamp", LocalDateTime.now().toString()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleRequestNotFoundException(RequestNotFoundException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(
                Map.of(
                        "message", exception.getMessage(),
                        "status", HttpStatus.NOT_FOUND.getReasonPhrase(),
                        "timestamp", LocalDateTime.now().toString()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleCompilationNotFoundException(CompilationNotFoundException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(
                Map.of(
                        "message", exception.getMessage(),
                        "status", HttpStatus.NOT_FOUND.getReasonPhrase(),
                        "timestamp", LocalDateTime.now().toString()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleCommentNotFoundException(CommentNotFoundException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(
                Map.of(
                        "message", exception.getMessage(),
                        "status", HttpStatus.NOT_FOUND.getReasonPhrase(),
                        "timestamp", LocalDateTime.now().toString()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleCategoryConflictException(CategoryConflictException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(
                Map.of(
                        "message", exception.getMessage(),
                        "status", HttpStatus.CONFLICT.getReasonPhrase(),
                        "timestamp", LocalDateTime.now().toString()
                ),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleUserConflictException(UserConflictException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(
                Map.of(
                        "message", exception.getMessage(),
                        "status", HttpStatus.CONFLICT.getReasonPhrase(),
                        "timestamp", LocalDateTime.now().toString()
                ),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleThrowable(Throwable throwable) {
        log.info(throwable.getMessage());
        throwable.printStackTrace();
        return new ResponseEntity<>(
                Map.of(
                        "message", throwable.getMessage(),
                        "reason", throwable.getLocalizedMessage(),
                        "status", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        "timestamp", LocalDateTime.now().toString()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}