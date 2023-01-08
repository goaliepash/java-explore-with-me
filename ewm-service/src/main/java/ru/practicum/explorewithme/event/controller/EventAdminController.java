package ru.practicum.explorewithme.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.event.model.dto.EventDto;
import ru.practicum.explorewithme.event.model.dto.EventRequestDto;
import ru.practicum.explorewithme.event.service.EventAdminService;

import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
@Slf4j
@RequiredArgsConstructor
public class EventAdminController {

    private final EventAdminService eventAdminService;

    @GetMapping
    public List<EventDto> get(
            @RequestParam(name = "users", required = false) List<Long> users,
            @RequestParam(name = "states", required = false) List<String> states,
            @RequestParam(name = "categories", required = false) List<Long> categories,
            @RequestParam(name = "rangeStart", required = false) String rangeStart,
            @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
            @RequestParam(name = "from", required = false, defaultValue = "0") int from,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size
    ) {
        log.info(
                "Выполнен запрос GET /admin/events?users={}&states={}&categories={}&rangeStart={}&rangeEnd={}&from={}&size={}.",
                users,
                states,
                categories,
                rangeStart,
                rangeEnd,
                from,
                size
        );
        return eventAdminService.get(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/{eventId}")
    public EventDto update(@PathVariable long eventId, @RequestBody EventRequestDto eventRequestDto) {
        log.info("Выполнен запрос PUT /admin/events/{}", eventId);
        return eventAdminService.update(eventId, eventRequestDto);
    }

    @PatchMapping("/{eventId}/publish")
    public EventDto publish(@PathVariable long eventId) {
        log.info("Выполнен запрос PATCH /admin/events/{}/publish", eventId);
        return eventAdminService.publish(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventDto reject(@PathVariable long eventId) {
        log.info("Выполнен запрос PATCH /admin/events/{}/reject", eventId);
        return eventAdminService.reject(eventId);
    }
}