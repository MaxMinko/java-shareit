package ru.practicum.shareit.user.db.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import ru.practicum.shareit.user.web.dto.UserDto;
import ru.practicum.shareit.user.db.model.User;

public class UserRepositoryImpl implements UserRepositoryCustom {
    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryImpl(@Lazy UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User updateUser(UserDto userDto, int userId) {
        User user = userRepository.findById(userId).get();
        if (user != null) {
            if (userDto.getEmail() != null) {
                user.setEmail(userDto.getEmail());
            }
            if (userDto.getName() != null) {
                user.setName(userDto.getName());
            }
        }
        return userRepository.save(user);
    }
}
