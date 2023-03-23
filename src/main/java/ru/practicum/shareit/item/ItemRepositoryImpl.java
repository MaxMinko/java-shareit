package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;


public class ItemRepositoryImpl implements ItemRepositoryCustom {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemRepositoryImpl(@Lazy ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Item updateItem(ItemDto itemDto, int userId, int itemId) {
        Item itemForUpdate = itemRepository.findByIdAndUserId(itemId, userId);
        if (itemForUpdate == null) {
            throw new ItemNotFoundException("Вещей для обнавления нет.");
        }
        if (itemDto.getName() != null && !itemDto.getName().isBlank()) {
            itemForUpdate.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null && !itemDto.getDescription().isBlank()) {
            itemForUpdate.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            itemForUpdate.setAvailable(itemDto.getAvailable());
        }
        return itemRepository.save(itemForUpdate);
    }


}
