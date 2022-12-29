package ru.practicum.explorewithme.stat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.stat.model.ViewStats;
import ru.practicum.explorewithme.stat.model.dto.EndpointHitDto;
import ru.practicum.explorewithme.stat.service.StatService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class StatController {

    private final StatService service;

    @PostMapping("/hit")
    public EndpointHitDto create(@RequestBody EndpointHitDto endpointHitDto) {
        log.info("Выполнен запрос POST /hit.");
        return service.create(endpointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStats> get(
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(required = false, defaultValue = "") List<String> uris,
            @RequestParam(required = false, defaultValue = "false") boolean unique
    ) {
        log.info("Выполнен запрос GET /stats?start={}&end={}&uris={}&unique={}.", start, end, uris, unique);
        return service.get(start, end, uris, unique);
    }
}