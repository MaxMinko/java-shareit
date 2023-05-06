package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.web.dto.ItemRequestDto;
import ru.practicum.shareit.request.web.dto.ItemRequestDtoForResponse;

import java.util.List;

public interface ItemRequestService {

    ItemRequestDto addRequest(ItemRequestDto itemRequestDto, int userId);

    List<ItemRequestDtoForResponse> getAllUserItemRequest(int userId);

    ItemRequestDtoForResponse getItemRequest(int userId, int requestId);

    List<ItemRequestDtoForResponse> getListWithOtherUserRequest(int userId, int from, int size);
}
