package ru.practicum.shareit.user.db.repository;

import ru.practicum.shareit.user.web.dto.UserDto;
import ru.practicum.shareit.user.db.model.User;

public interface UserRepositoryCustom {
    User updateUser(UserDto userDto, int userId);
}
