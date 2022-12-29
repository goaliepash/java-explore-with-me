package ru.practicum.explorewithme.event.service;

import ru.practicum.explorewithme.event.model.dto.EventDto;
import ru.practicum.explorewithme.event.model.dto.EventRequestDto;
import ru.practicum.explorewithme.request.model.dto.RequestDto;

import java.util.List;

public interface EventPrivateService {

    List<EventDto> get(long userId, int from, int size);

    EventDto update(long userId, EventRequestDto eventRequestDto);

    EventDto create(long userId, EventRequestDto eventRequestDto);

    EventDto get(long userId, long eventId);

    EventDto cancel(long userId, long eventId);

    List<RequestDto> getEventRequests(long userId, long eventId);

    RequestDto confirmRequest(long userId, long eventId, long reqId);

    RequestDto rejectRequest(long userId, long eventId, long reqId);
}