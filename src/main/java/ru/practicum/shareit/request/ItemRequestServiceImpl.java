package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoForResponse;
import ru.practicum.shareit.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemRequestServiceImpl implements ItemRequestService {
    ItemService itemService;
    ItemRequestRepository itemRequestRepository;
    UserService userService;


    @Autowired
    public ItemRequestServiceImpl(ItemRequestRepository itemRequestRepository, @Lazy UserService userService,
                                  @Lazy ItemService itemService) {
        this.itemRequestRepository = itemRequestRepository;
        this.userService = userService;
        this.itemService = itemService;
    }

    @Override
    public ItemRequestDto addRequest(ItemRequestDto itemRequestDto, int userId) {
        userService.getUser(userId);
        return ItemRequestMapper.toItemRequestDto(itemRequestRepository
                .save(ItemRequestMapper.toItemRequest(itemRequestDto, userId)));
    }

    @Override
    public List<ItemRequestDtoForResponse> getAllUserItemRequest(int userId) {
        userService.getUser(userId);
        List<ItemRequestDtoForResponse> itemRequests = itemRequestRepository.findByUserId(userId)
                .stream().map(ItemRequestMapper::toItemRequestDtoForResponse)
                .collect(Collectors.toList());
        for (ItemRequestDtoForResponse itemRequestDtoForResponse : itemRequests) {
            itemRequestDtoForResponse.setItems(itemService.getItemWithRequest(
                            itemRequestDtoForResponse.getId()).stream()
                    .map(ItemMapper::toItemDtoForRequest).collect(Collectors.toList()));
        }
        return itemRequests;
    }

    @Override
    public ItemRequestDtoForResponse getItemRequest(int userId, int requestId) {
        userService.getUser(userId);
        ItemRequest itemRequest = itemRequestRepository
                .findById(requestId).orElseThrow(() -> new UserNotFoundException("Запрос не был найден."));
        ItemRequestDtoForResponse itemRequestDtoForResponse = ItemRequestMapper
                .toItemRequestDtoForResponse(itemRequest);
        itemRequestDtoForResponse.setItems(itemService.getItemWithRequest(
                        itemRequestDtoForResponse.getId()).stream()
                .map(ItemMapper::toItemDtoForRequest).collect(Collectors.toList()));
        return itemRequestDtoForResponse;
    }

    @Override
    public List<ItemRequestDtoForResponse> getListWithOtherUserRequest(int userId, int from, int size) {
        Page<ItemRequest> page = itemRequestRepository.findByUserIdIsNot(userId, PageRequest.of(from, size
                , Sort.by("created")));
        List<ItemRequestDtoForResponse> itemRequestDtoForResponseList = page.toList().stream()
                .map(ItemRequestMapper::toItemRequestDtoForResponse).collect(Collectors.toList());
        for (ItemRequestDtoForResponse itemRequestDtoForResponse : itemRequestDtoForResponseList) {
            itemRequestDtoForResponse.setItems(itemService.getItemWithRequest(
                            itemRequestDtoForResponse.getId()).stream()
                    .map(ItemMapper::toItemDtoForRequest).collect(Collectors.toList()));
        }
        return itemRequestDtoForResponseList;
    }
}
