package ru.practicum.shareit.item;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.Validator;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.*;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final Validator validator;

    @Autowired
    ItemServiceImpl(ItemStorage itemStorage) {
        this.itemStorage = itemStorage;
        this.validator = new Validator(this.itemStorage);
    }

    @Override
    public ItemDto addItem(ItemDto itemDto, int userId) {
        validator.checkItemDto(itemDto);
        return itemStorage.addItem(ItemMapper.toItem(itemDto), userId);
    }

    @Override
    public ItemDto updateItem(ItemDto itemDto, int userId, int itemId) {
        return itemStorage.updateItem(itemDto, userId, itemId);
    }

    @Override
    public ItemDto getItem(int itemId) {
        return itemStorage.getItem(itemId).get();
    }

    @Override
    public List<ItemDto> getAllUserItems(int userId) {
        return itemStorage.getAllUserItems(userId);
    }

    @Override
    public List<ItemDto> getItemByDescription(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return itemStorage.getItemByDescription(text);
    }
}
