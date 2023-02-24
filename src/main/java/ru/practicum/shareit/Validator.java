package ru.practicum.shareit;

import ru.practicum.shareit.exception.DuplicateEmailException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemStorage;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.UserStorage;
import ru.practicum.shareit.user.dto.UserDto;

public class Validator {
    UserStorage userStorage;
    ItemStorage itemStorage;

    public Validator(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public Validator(ItemStorage itemStorage) {
        this.itemStorage = itemStorage;
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

    public void checkUserDtoEmail(String email) {
        if (email.isBlank()) {
            throw new ValidationException("Емейл не может быть пустым");
        }
        if (!userStorage.getAllUsers().stream().filter(x -> x.getEmail().equals(email)).findFirst().isEmpty()) {
            throw new DuplicateEmailException("Такой емейл есть.");
        }
        if (!email.contains("@")) {
            throw new ValidationException("Емейл должен содержать @");
        }
    }

    public void checkUserDtoForUpdate(UserDto userDto) {
        if (userDto.getEmail() == null || userDto.getName() == null) {
            if (userDto.getName() == null) {
                checkUserDtoEmail(userDto.getEmail());
            }
            if (userDto.getEmail() == null) {
                checkUserDtoName(userDto.getName());
            }
        } else {
            checkUserDtoName(userDto.getName());
            checkUserDtoEmail(userDto.getEmail());
        }
    }

    public void checkItemDto(ItemDto itemDto) {
        if (itemDto.getName() == null || itemDto.getName().isBlank()) {
            throw new ValidationException("Имя не может быть пустым.");
        }
        if (itemDto.getDescription() == null || itemDto.getDescription().isBlank()) {
            throw new ValidationException("Описание не может быть пустым.");
        }
        if (itemDto.getAvailable() == null) {
            throw new ValidationException("Статус не может быть пустым.");
        }

    }

}
