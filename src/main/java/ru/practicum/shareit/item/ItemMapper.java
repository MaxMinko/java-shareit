package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoForRequest;
import ru.practicum.shareit.item.model.Item;

@Component
public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getDescription(), item.getAvailable()
                ,item.getRequestId());
    }

    public static Item toItem(ItemDto itemDto, int userId) {
        if(itemDto.getRequestId()==null){
            return new Item(itemDto.getId(), itemDto.getName(), itemDto.getDescription(), itemDto.getAvailable()
                    , userId);
        }else {
            return new Item(itemDto.getId(), itemDto.getName(), itemDto.getDescription(), itemDto.getAvailable()
                    , userId,itemDto.getRequestId());
        }
    }

    public static ItemDtoForRequest toItemDtoForRequest(Item item){
        return new ItemDtoForRequest(item.getId(),item.getName(),item.getDescription()
                ,item.getAvailable(),item.getRequestId());
    }
}
