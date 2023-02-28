package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto addItem(ItemDto itemDto, int userId);

    ItemDto updateItem(ItemDto itemDto, int userId, int itemId);

    ItemDto getItem(int itemId);

    List<ItemDto> getAllUserItems(int userId);

    List<ItemDto> getItemByDescription(String text);
}
