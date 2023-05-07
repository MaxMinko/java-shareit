package ru.practicum.shareit.user.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.practicum.shareit.user.web.dto.UserDto;
import ru.practicum.shareit.user.db.model.User;

public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {
    User updateUser(UserDto userDto, int userId);

}