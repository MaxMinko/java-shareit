package ru.practicum.shareit.user;


import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.validator.UserValidator;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserValidator userValidator;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        userValidator = new UserValidator(userRepository);
    }
    @Transactional
    @Override
    public UserDto addUser(UserDto userDto) {
        userValidator.checkUserDtoForCreate(userDto);
        return UserMapper.toUserDto(userRepository.save(UserMapper.toUser(userDto)));
    }
    @Transactional
    @Override
    public UserDto updateUser(UserDto userDto, int userId) {
        userValidator.checkUserDtoForUpdate(userDto, userId);
        return UserMapper.toUserDto(userRepository.updateUser(userDto, userId));
    }

    @Override
    public UserDto getUser(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не был найден."));
        return UserMapper.toUserDto(user);
    }
    @Transactional
    @Override
    public void userDelete(int userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }
}
