package ru.practicum.explorewithme.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.constraint_group.Create;
import ru.practicum.explorewithme.user.model.dto.UserDto;
import ru.practicum.explorewithme.user.service.UserAdminService;

import java.util.List;

@RestController
@RequestMapping(path = "/admin/users")
@Slf4j
@RequiredArgsConstructor
public class UserAdminController {

    private final UserAdminService service;

    @GetMapping
    public List<UserDto> get(@RequestParam(required = false) List<Long> ids) {
        log.info("Выполнен запрос GET /admin/users?ids={}", ids);
        return service.getByIds(ids);
    }

    @PostMapping
    public UserDto create(@Validated(Create.class) @RequestBody UserDto userDto) {
        log.info("Выполнен запрос POST /admin/users.");
        return service.create(userDto);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable long userId) {
        log.info("Выполнен запрос DELETE /admin/users/{}.", userId);
        service.delete(userId);
    }
}