package ru.practicum.shareit.validator;

import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;

public class ItemValidator {

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
