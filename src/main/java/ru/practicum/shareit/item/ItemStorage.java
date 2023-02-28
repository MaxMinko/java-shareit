package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemStorage {
    ItemDto addItem(Item item, int userId);

    ItemDto updateItem(ItemDto itemDto, int userId, int itemId);

    Optional<ItemDto> getItem(int itemId);

    List<ItemDto> getAllUserItems(int userId);

    List<ItemDto> getItemByDescription(String text);
}
