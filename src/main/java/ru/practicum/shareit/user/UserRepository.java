package ru.practicum.shareit.user;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {
    User updateUser(UserDto userDto, int userId);
}