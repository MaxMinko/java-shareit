package ru.practicum.shareit.validator;

import ru.practicum.shareit.exception.DuplicateEmailException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.UserStorage;
import ru.practicum.shareit.user.dto.UserDto;

public class UserValidator {
    private UserStorage userStorage;

    public UserValidator(UserStorage userStorage) {
        this.userStorage = userStorage;
    }


    public void checkUserDtoForCreate(UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
            throw new ValidationException("Емейл не может быть пустым");
        }
        if (!userStorage.getAllUsers().stream().filter(x -> x.getEmail().equals(userDto.getEmail()))
                .findFirst().isEmpty()) {
            throw new DuplicateEmailException("Такой емейл есть.");
        }
        if (!userDto.getEmail().contains("@")) {
            throw new ValidationException("Емейл должен содержать @");
        }
        if (userDto.getName() == null || userDto.getName().isBlank()) {
            throw new ValidationException("Имя польователя не должно быть пустым.");
        }
    }

    public void checkUserDtoName(String name) {
        if (name.isBlank()) {
            throw new ValidationException("Имя польователя не должно быть пустым.");
        }
    }

    public void checkUserDtoEmail(String email,int userId) {
        if (email.isBlank()) {
            throw new ValidationException("Емейл не может быть пустым");
        }
        if (!userStorage.getAllUsers().stream()
                .filter(x -> x.getEmail().equals(email)&&x.getId()!=userId).findFirst().isEmpty()) {
            throw new DuplicateEmailException("Такой емейл есть.");
        }
        if (!email.contains("@")) {
            throw new ValidationException("Емейл должен содержать @");
        }
    }

    public void checkUserDtoForUpdate(UserDto userDto,int userId) {
        if (userDto.getEmail() == null || userDto.getName() == null) {
            if (userDto.getName() == null) {
                checkUserDtoEmail(userDto.getEmail(),userId);
            }
            if (userDto.getEmail() == null) {
                checkUserDtoName(userDto.getName());
            }
        } else {
            checkUserDtoName(userDto.getName());
            checkUserDtoEmail(userDto.getEmail(),userId);
        }
    }

}
