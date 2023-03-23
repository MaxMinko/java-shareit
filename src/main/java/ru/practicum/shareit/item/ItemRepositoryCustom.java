package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;


public interface ItemRepositoryCustom {
    Item updateItem(ItemDto item, int userId, int itemId);

}
