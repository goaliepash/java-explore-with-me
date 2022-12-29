package ru.practicum.explorewithme.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.explorewithme.category.controller.CategoryAdminController;
import ru.practicum.explorewithme.category.controller.CategoryPublicController;
import ru.practicum.explorewithme.compilation.controller.CompilationPublicController;
import ru.practicum.explorewithme.event.controller.EventAdminController;
import ru.practicum.explorewithme.event.controller.EventPrivateController;
import ru.practicum.explorewithme.exception.category.CategoryConflictException;
import ru.practicum.explorewithme.exception.category.CategoryNotFoundException;
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

import java.util.Map;

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
    private ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(Map.of("status", HttpStatus.BAD_REQUEST.getReasonPhrase()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleEventBadRequestException(EventBadRequestException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleCompilationBadRequestException(CompilationBadRequestException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleEventForbiddenException(EventForbiddenException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleCategoryNotFoundException(CategoryNotFoundException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleEventNotFoundException(EventNotFoundException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleEventStateNotFoundException(EventStateNotFoundException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleRequestNotFoundException(RequestNotFoundException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleCompilationNotFoundException(CompilationNotFoundException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleCategoryConflictException(CategoryConflictException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity<>(
                Map.of(
                        "message", exception.getMessage(),
                        "status", HttpStatus.CONFLICT.getReasonPhrase()
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
                        "status", HttpStatus.CONFLICT.getReasonPhrase()
                ),
                HttpStatus.CONFLICT
        );
    }
}