package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

public interface UserRepositoryCustom {
    User updateUser(UserDto userDto, int userId);
}
