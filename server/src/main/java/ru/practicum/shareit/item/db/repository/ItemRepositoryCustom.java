package ru.practicum.shareit.item.db.repository;

import ru.practicum.shareit.item.web.dto.ItemDto;
import ru.practicum.shareit.item.db.model.Item;


public interface ItemRepositoryCustom {
    Item updateItem(ItemDto item, int userId, int itemId);

}
