package ru.practicum.shareit.user;

import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserStorage {
    User addUser(User user);

    User updateUser(User user, int userId);

    User getUser(int userId);

    void userDelete(int userId);

    List<User> getAllUsers();
}
