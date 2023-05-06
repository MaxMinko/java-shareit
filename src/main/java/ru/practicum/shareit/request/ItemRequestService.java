package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoForResponse;

import java.util.List;

public interface ItemRequestService {

    ItemRequestDto addRequest(ItemRequestDto itemRequestDto, int userId);

    List<ItemRequestDtoForResponse> getAllUserItemRequest(int userId);

    ItemRequestDtoForResponse getItemRequest(int userId, int requestId);

    List<ItemRequestDtoForResponse> getListWithOtherUserRequest(int userId, int from, int size);
}
