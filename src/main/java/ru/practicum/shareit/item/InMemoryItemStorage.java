package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryItemStorage implements ItemStorage {
    private Map<Integer, List<Item>> items = new HashMap<>();
    private int itemId = 1;

    @Override
    public ItemDto addItem(Item item, int userId) {
        if (item.getId() == 0) {
            item.setId(itemId++);
        }
        if (items.containsKey(userId)) {
            items.get(userId).add(item);
        } else {
            List<Item> itemList = new ArrayList<>();
            itemList.add(item);
            items.put(userId, itemList);
        }
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto updateItem(ItemDto itemDto, int userId, int itemId) {
        Boolean userItem = false;
        List<Item> itemList = items.get(userId);
        if (itemList != null) {
            for (Item itemForUpdate : itemList) {
                if (itemForUpdate.getId() == itemId) {
                    deleteItem(userId, ItemMapper.toItemDto(itemForUpdate));
                    if (itemDto.getName() != null && !itemDto.getName().isBlank()) {
                        itemForUpdate.setName(itemDto.getName());
                    }
                    if (itemDto.getDescription() != null && !itemDto.getDescription().isBlank()) {
                        itemForUpdate.setDescription(itemDto.getDescription());
                    }
                    if (itemDto.getAvailable() != null) {
                        itemForUpdate.setAvailable(itemDto.getAvailable());
                    }
                    addItem(itemForUpdate, userId);
                    userItem = true;
                    break;
                }
            }
            if (userItem == false) {
                throw new ItemNotFoundException("Нет вещей.");
            }
        } else {
            throw new ItemNotFoundException("Пользователь не может редактировать чужую вещь.");
        }
        return getItem(itemId).get();
    }

    @Override
    public Optional<ItemDto> getItem(int itemId) {
        Optional<ItemDto> itemDto = items.entrySet().stream()
                .flatMap(s -> s.getValue().stream())
                .filter(x -> x.getId() == itemId).findFirst()
                .map(ItemMapper::toItemDto);
        return itemDto;
    }

    @Override
    public List<ItemDto> getAllUserItems(int userId) {
        return items.entrySet().stream().filter(s -> s.getKey() == userId).
                flatMap(x -> x.getValue().stream())
                .map(ItemMapper::toItemDto).collect(Collectors.toList());
    }

    @Override
    public List<ItemDto> getItemByDescription(String text) {
        return items.entrySet().stream().flatMap(x -> x.getValue().stream())
                .filter(x -> x.getAvailable() == true &&
                        (x.getName().toLowerCase().contains(text.toLowerCase()) ||
                                x.getDescription().toLowerCase()
                                        .contains(text.toLowerCase()))).map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    private void deleteItem(int userId, ItemDto itemDto) {
        items.get(userId).remove(ItemMapper.toItem(itemDto,userId));
    }
}
