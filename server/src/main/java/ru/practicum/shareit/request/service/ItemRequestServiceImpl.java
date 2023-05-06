package ru.practicum.shareit.request.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.request.web.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.db.model.ItemRequest;
import ru.practicum.shareit.request.db.repository.ItemRequestRepository;
import ru.practicum.shareit.request.web.dto.ItemRequestDto;
import ru.practicum.shareit.request.web.dto.ItemRequestDtoForResponse;
import ru.practicum.shareit.user.service.UserService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemRequestServiceImpl implements ItemRequestService {
    private ItemRequestRepository itemRequestRepository;
    private UserService userService;

    @Autowired
    public ItemRequestServiceImpl(ItemRequestRepository itemRequestRepository, @Lazy UserService userService) {
        this.itemRequestRepository = itemRequestRepository;
        this.userService = userService;
    }

    @Transactional
    @Override
    public ItemRequestDto addRequest(ItemRequestDto itemRequestDto, int userId) {
        userService.getUser(userId);
        return ItemRequestMapper.toItemRequestDto(itemRequestRepository
                .save(ItemRequestMapper.toItemRequest(itemRequestDto, userId)));
    }

    @Override
    public List<ItemRequestDtoForResponse> getAllUserItemRequest(int userId) {
        userService.getUser(userId);
        return itemRequestRepository.findByUserId(userId).stream()
                .map(ItemRequestMapper::toItemRequestDtoForResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ItemRequestDtoForResponse getItemRequest(int userId, int requestId) {
        userService.getUser(userId);
        ItemRequest itemRequest = itemRequestRepository
                .findById(requestId).orElseThrow(() -> new UserNotFoundException("Запрос не был найден."));
        ItemRequestDtoForResponse itemRequestDtoForResponse = ItemRequestMapper
                .toItemRequestDtoForResponse(itemRequest);
        return itemRequestDtoForResponse;
    }

    @Override
    public List<ItemRequestDtoForResponse> getListWithOtherUserRequest(int userId, int from, int size) {
        Page<ItemRequest> page = itemRequestRepository.findByUserIdIsNot(userId, PageRequest.of(from, size,
                Sort.by("created")));
        List<ItemRequestDtoForResponse> itemRequestDtoForResponseList = page.toList().stream()
                .map(ItemRequestMapper::toItemRequestDtoForResponse).collect(Collectors.toList());
        return itemRequestDtoForResponseList;
    }
}
