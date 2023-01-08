package ru.practicum.explorewithme.stat.service;

import ru.practicum.explorewithme.stat.model.ViewStats;
import ru.practicum.explorewithme.stat.model.dto.EndpointHitDto;

import java.util.List;

public interface StatService {

    EndpointHitDto create(EndpointHitDto endpointHitDto);

    List<ViewStats> get(String start, String end, List<String> uris, Boolean unique);
}