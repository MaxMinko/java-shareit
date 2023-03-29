package ru.practicum.shareit.item;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.dto.BookingDtoForResponse;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.item.dto.ItemDto;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    private final BookingService bookingService;
    private final UserService userService;

    private final CommentRepository commentRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, UserService userService, CommentRepository commentRepository,
                           BookingService bookingService) {
        this.itemRepository = itemRepository;
        this.userService = userService;
        this.commentRepository = commentRepository;
        this.bookingService = bookingService;
    }
    @Transactional
    @Override
    public ItemDto addItem(ItemDto itemDto, int userId) {
        Item item = ItemMapper.toItem(itemDto, userId);
        return ItemMapper.toItemDto(itemRepository.save(item));
    }
    @Transactional
    @Override
    public ItemDto updateItem(ItemDto itemDto, int userId, int itemId) {
        return ItemMapper.toItemDto(itemRepository.updateItem(itemDto, userId, itemId));
    }

    @Override
    public ItemDto getItemDto(int itemId, int userId) {
        Item item = getItem(itemId);
        ItemDto itemDto = ItemMapper.toItemDto(item);
        if (item.getUserId() == userId) {
            BookingDtoForResponse nextBooking = bookingService.findNextBookingForItem(itemId);
            BookingDtoForResponse lastBooking = bookingService.findLastBookingForItem(itemId);
            if (nextBooking.getId() != 0) {
                itemDto.setNextBooking(BookingMapper.toBookingDtoWithIdAndBookerId(nextBooking));
            }
            if (lastBooking.getId() != 0) {
                itemDto.setLastBooking(BookingMapper.toBookingDtoWithIdAndBookerId(lastBooking));
            }
        }
        itemDto.setComments(commentRepository.findByItemId(itemId).stream()
                .map(CommentMapper::toCommentDto).collect(Collectors.toList()));
        return itemDto;
    }

    @Override
    public List<ItemDto> getAllUserItems(int userId) {
        List<ItemDto> itemDtoList = itemRepository.findItemsByUserIdOrderByIdAsc(userId).stream()
                .map(ItemMapper::toItemDto).collect(Collectors.toList());
        for (ItemDto itemDto : itemDtoList) {
            BookingDtoForResponse nextBooking = bookingService.findNextBookingForItem(itemDto.getId());
            BookingDtoForResponse lastBooking = bookingService.findLastBookingForItem(itemDto.getId());
            if (nextBooking != null && lastBooking != null) {
                if (nextBooking.getId() != 0 && lastBooking.getId() != 0) {
                    itemDto.setNextBooking(BookingMapper.toBookingDtoWithIdAndBookerId(nextBooking));
                }
                if (lastBooking.getId() != 0) {
                    itemDto.setLastBooking(BookingMapper.toBookingDtoWithIdAndBookerId(lastBooking));
                }
            }
        }
        return itemDtoList;
    }

    @Override
    public List<ItemDto> getItemByDescription(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return itemRepository.findByNameAndDescriptionContaining(text).stream().map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public Item getItem(int itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException("Вещь не найдена."));
    }
    @Transactional
    @Override
    public CommentDto addCommentDto(CommentDto commentDto, int userId, int itemId) {
        Item item = getItem(itemId);
        User user = UserMapper.toUser(userService.getUser(userId));
        Integer quantityOfItemReservations = bookingService.findBookingForComments(userId, itemId);
        if (quantityOfItemReservations < 1) {
            throw new ValidationException("Пользователь не может оставить отзыв на эту вещь.");
        }
        if(commentDto.getCreated()==null) {
            commentDto.setCreated(LocalDateTime.now());
        }
        return CommentMapper.toCommentDto(commentRepository.save(CommentMapper.toComment(commentDto, user, item)));
    }

    @Override
    public List<Item> getItemWithRequest(int requestId) {
        return itemRepository.getItemWithRequest(requestId);
    }
}
