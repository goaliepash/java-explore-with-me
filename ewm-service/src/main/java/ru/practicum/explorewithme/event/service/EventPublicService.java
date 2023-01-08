package ru.practicum.explorewithme.event.service;

import ru.practicum.explorewithme.event.model.dto.EventDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventPublicService {

    List<EventDto> get(
            String text,
            List<Long> categories,
            Boolean paid,
            String rangeStart,
            String rangeEnd,
            Boolean onlyAvailable,
            String sort,
            int from,
            int size,
            HttpServletRequest request
    );

    EventDto get(long eventId, HttpServletRequest request);
}