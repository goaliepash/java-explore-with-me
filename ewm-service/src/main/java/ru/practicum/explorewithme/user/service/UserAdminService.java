package ru.practicum.explorewithme.user.service;

import ru.practicum.explorewithme.user.model.dto.UserDto;

import java.util.List;

public interface UserAdminService {

    UserDto create(UserDto userDto);

    List<UserDto> getByIds(List<Long> ids);

    void delete(long userId);
}