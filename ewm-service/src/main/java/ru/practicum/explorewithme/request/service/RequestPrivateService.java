package ru.practicum.explorewithme.request.service;

import ru.practicum.explorewithme.request.model.dto.RequestDto;

import java.util.List;

public interface RequestPrivateService {

    List<RequestDto> get(long userId);

    RequestDto create(long userId, long eventId);

    RequestDto cancel(long userId, long requestId);
}