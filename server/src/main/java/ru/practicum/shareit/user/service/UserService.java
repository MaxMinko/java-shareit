package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.web.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto addUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, int userId);

    UserDto getUser(int userId);

    void userDelete(int userId);

    List<UserDto> getAllUsers();
}
