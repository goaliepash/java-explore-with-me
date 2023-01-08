package ru.practicum.explorewithme.exception.event;

public class EventForbiddenException extends RuntimeException {

    public EventForbiddenException(String message) {
        super(message);
    }
}