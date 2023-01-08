package ru.practicum.explorewithme.exception.user;

public class UserConflictException extends RuntimeException {

    public UserConflictException(String message) {
        super(message);
    }
}