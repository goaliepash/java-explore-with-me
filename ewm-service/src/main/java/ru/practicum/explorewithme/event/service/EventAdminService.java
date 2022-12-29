package ru.practicum.explorewithme.event.service;

import ru.practicum.explorewithme.event.model.dto.EventDto;
import ru.practicum.explorewithme.event.model.dto.EventRequestDto;

import java.util.List;

public interface EventAdminService {

    List<EventDto> get(List<Long> users, List<String> states, List<Long> categories, String rangeStart, String rangeEnd, int from, int size);

    EventDto update(long eventId, EventRequestDto eventRequestDto);

    EventDto publish(long eventId);

    EventDto reject(long eventId);
}