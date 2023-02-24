package ru.practicum.shareit.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.Validator;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;
    private final Validator validator;

    @Autowired
    UserServiceImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
        this.validator = new Validator(this.userStorage);
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        validator.checkUserDtoForCreate(userDto);
        return UserMapper.toUserDto(userStorage.addUser(UserMapper.toUser(userDto)));
    }

    @Override
    public UserDto updateUser(UserDto userDto, int userId) {
        validator.checkUserDtoForUpdate(userDto);
        return UserMapper.toUserDto(userStorage.updateUser(UserMapper.toUser(userDto), userId));
    }

    @Override
    public UserDto getUser(int userId) {
        User user = userStorage.getUser(userId);
        if (user == null) {
            throw new UserNotFoundException("Пользователь не был найден.");
        }
        return UserMapper.toUserDto(user);
    }

    @Override
    public void userDelete(int userId) {
        userStorage.userDelete(getUser(userId).getId());
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userStorage.getAllUsers().stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }
}
