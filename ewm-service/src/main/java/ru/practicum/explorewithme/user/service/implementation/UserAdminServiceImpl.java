package ru.practicum.explorewithme.user.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.exception.user.UserConflictException;
import ru.practicum.explorewithme.user.model.User;
import ru.practicum.explorewithme.user.model.dto.UserDto;
import ru.practicum.explorewithme.user.model.dto.UserMapper;
import ru.practicum.explorewithme.user.repository.UserRepository;
import ru.practicum.explorewithme.user.service.UserAdminService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAdminServiceImpl implements UserAdminService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> getByIds(List<Long> ids) {
        return userRepository
                .findAllById(ids)
                .stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserDto create(UserDto userDto) {
        checkIfUserNameExists(userDto.getName());
        User user = UserMapper.fromUserDto(userDto);
        return UserMapper.toUserDto(userRepository.save(user));
    }

    @Transactional
    @Override
    public void delete(long userId) {
        userRepository.deleteById(userId);
    }

    private void checkIfUserNameExists(String name) {
        if (!userRepository.findAllByName(name).isEmpty()) {
            throw new UserConflictException("Имя пользователя должно быть уникальным.");
        }
    }
}