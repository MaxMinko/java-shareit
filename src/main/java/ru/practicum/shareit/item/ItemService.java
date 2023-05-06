package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {
    ItemDto addItem(ItemDto itemDto, int userId);

    ItemDto updateItem(ItemDto itemDto, int userId, int itemId);

    ItemDto getItemDto(int itemId, int userId);

    List<ItemDto> getAllUserItems(int userId);

    List<ItemDto> getItemByDescription(String text);

    Item getItem(int itemId);

    CommentDto addCommentDto(CommentDto commentDto, int userId, int itemId);
    List<Item> getItemWithRequest(int requestId);
}
