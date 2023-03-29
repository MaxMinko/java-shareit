package ru.practicum.shareit.request;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoForResponse;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class ItemRequestMapper {
    public static ItemRequest toItemRequest(ItemRequestDto itemRequestDto, int userId) {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription(itemRequestDto.getDescription());
        itemRequest.setCreated(LocalDateTime.now());
        itemRequest.setUserId(userId);
        return itemRequest;
    }

    public static ItemRequestDto toItemRequestDto(ItemRequest itemRequest) {
        return new ItemRequestDto(itemRequest.getId(), itemRequest.getDescription(), itemRequest.getCreated());
    }

    public static ItemRequestDtoForResponse toItemRequestDtoForResponse(ItemRequest itemRequest) {
        if(itemRequest.getItems()==null){
            return new ItemRequestDtoForResponse(itemRequest.getId(), itemRequest.getDescription()
                    , itemRequest.getCreated());
        }
        return new ItemRequestDtoForResponse(itemRequest.getId(), itemRequest.getDescription()
                , itemRequest.getCreated(),itemRequest.getItems().stream()
                .map(ItemMapper::toItemDtoForRequest).collect(Collectors.toList()));
    }
}
