package ru.practicum.explorewithme.exception.event;

public class EventBadRequestException extends RuntimeException {

    public EventBadRequestException(String message) {
        super(message);
    }
}