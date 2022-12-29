package ru.practicum.explorewithme.exception.event;

public class EventStateNotFoundException extends RuntimeException {

    public EventStateNotFoundException(String message) {
        super(message);
    }
}