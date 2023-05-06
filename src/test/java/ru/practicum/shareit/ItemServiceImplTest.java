package ru.practicum.shareit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {
    @Mock
    private ItemRepository itemRepository;
    @Mock
    private BookingService bookingService;
    @Mock
    private UserService userService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookingRepository bookingRepository;
    @InjectMocks
    ItemServiceImpl itemService;


    @Test
    void addItem_whenItemValid_thenReturnedSavedItem() {
        int userOwnerId = 1;
        Item savedItem = new Item(1, "test", "testDescription", true, userOwnerId);

        Mockito.when(itemRepository.save(savedItem)).thenReturn(savedItem);
        ItemDto actualItem = itemService.addItem(ItemMapper.toItemDto(savedItem), userOwnerId);

        verify(itemRepository).save(savedItem);
        Assertions.assertEquals(savedItem.getId(), actualItem.getId());
        Assertions.assertEquals(savedItem.getName(), actualItem.getName());
        Assertions.assertEquals(savedItem.getDescription(), actualItem.getDescription());
        Assertions.assertEquals(savedItem.getAvailable(), actualItem.getAvailable());
    }

    @Test
    void updateItem_whenItemForUpdateValid_thenReturnedSavedItem() {
        int userOwnerId = 1;
        int itemIdForUpdate = 1;
        ItemDto itemForUpdate = new ItemDto(1, "test", "testDescription", true, userOwnerId);

        Mockito.when(itemRepository.updateItem(itemForUpdate, userOwnerId, itemIdForUpdate))
                .thenReturn(ItemMapper.toItem(itemForUpdate, userOwnerId));
        ItemDto actualItem = itemService.updateItem(itemForUpdate, userOwnerId, itemIdForUpdate);

        verify(itemRepository).updateItem(itemForUpdate, userOwnerId, itemIdForUpdate);
        Assertions.assertEquals(itemForUpdate.getId(), actualItem.getId());
        Assertions.assertEquals(itemForUpdate.getName(), actualItem.getName());
        Assertions.assertEquals(itemForUpdate.getDescription(), actualItem.getDescription());
        Assertions.assertEquals(itemForUpdate.getAvailable(), actualItem.getAvailable());
    }

    @Test
    void getItem_whenItemFound_thenReturnedItem() {
        int userOwnerId = 1;
        int itemId = 1;
        Item expectedItem = new Item(1, "test", "testDescription", true, userOwnerId);

        Mockito.when(itemRepository.findById(itemId)).thenReturn(Optional.of(expectedItem));
        Item actualItem = itemRepository.findById(itemId).get();

        verify(itemRepository).findById(itemId);
        Assertions.assertEquals(expectedItem.getId(), actualItem.getId());
        Assertions.assertEquals(expectedItem.getName(), actualItem.getName());
        Assertions.assertEquals(expectedItem.getDescription(), actualItem.getDescription());
        Assertions.assertEquals(expectedItem.getAvailable(), actualItem.getAvailable());
    }

    @Test
    void getItem_whenItemNotFound_thenReturnedItemNotFoundException() {
        int itemId = 0;

        Mockito.when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ItemNotFoundException.class, () -> itemService.getItem(itemId));
        verify(itemRepository).findById(itemId);
    }

    @Test
    void getAllUserItems_whenItemFound_thenReturnedItems() {
        int userOwnerId = 1;
        Item expectedItem = new Item(1, "test", "testDescription", true, userOwnerId);
        List<Item> expectedItemList = new ArrayList<>();
        expectedItemList.add(expectedItem);

        Mockito.when(itemRepository.findItemsByUserIdOrderByIdAsc(userOwnerId)).thenReturn(expectedItemList);
        List<ItemDto> actualItemList = itemService.getAllUserItems(userOwnerId);

        verify(itemRepository).findItemsByUserIdOrderByIdAsc(userOwnerId);
        Assertions.assertEquals(expectedItemList.size(), actualItemList.size());
        Assertions.assertEquals(expectedItemList.get(0).getId(), actualItemList.get(0).getId());
        Assertions.assertEquals(expectedItemList.get(0).getName(), actualItemList.get(0).getName());
        Assertions.assertEquals(expectedItemList.get(0).getDescription(), actualItemList.get(0).getDescription());
    }

    @Test
    void getItemByDescription_whenItemFound_thenReturnedItems() {
        int userOwnerId = 1;
        String text = "test";
        Item expectedItem = new Item(1, "test", "testDescription", true, userOwnerId);
        List<Item> expectedItemList = new ArrayList<>();
        expectedItemList.add(expectedItem);

        Mockito.when(itemRepository.findByNameAndDescriptionContaining(text)).thenReturn(expectedItemList);
        List<ItemDto> actualItemList = itemService.getItemByDescription(text);

        verify(itemRepository).findByNameAndDescriptionContaining(text);
        Assertions.assertEquals(expectedItemList.size(), actualItemList.size());
        Assertions.assertEquals(expectedItemList.get(0).getId(), actualItemList.get(0).getId());
        Assertions.assertEquals(expectedItemList.get(0).getName(), actualItemList.get(0).getName());
        Assertions.assertEquals(expectedItemList.get(0).getDescription(), actualItemList.get(0).getDescription());
    }

    @Test
    void getItemByDescription_whenTextForSearchIsBlank_thenReturnedEmptyList() {
        String text = "";

        List<ItemDto> actualItemList = itemService.getItemByDescription(text);

        verify(itemRepository, never()).findByNameAndDescriptionContaining(text);
        Assertions.assertEquals(0, actualItemList.size());
    }

    @Test
    void addComment_whenItemFoundAndUserFound_thenReturnedComment() {
        int userOwnerId = 1;
        int itemForCommentId = 1;
        Item expectedItem = new Item(1, "test", "testDescription", true, userOwnerId);
        User expectedUser = new User(1, "Test", "test@email");
        CommentDto commentDto = new CommentDto(1, "test", "Test", itemForCommentId
                , LocalDateTime.now());

        Mockito.when(itemRepository.findById(userOwnerId)).thenReturn(Optional.of(expectedItem));
        Mockito.when(userService.getUser(userOwnerId)).thenReturn(UserMapper.toUserDto(expectedUser));
        Mockito.when(bookingService.findBookingForComments(userOwnerId, itemForCommentId)).thenReturn(1);
        Mockito.when(commentRepository.save(CommentMapper.toComment(commentDto, expectedUser, expectedItem)))
                .thenReturn(CommentMapper.toComment(commentDto, expectedUser, expectedItem));
        CommentDto commentDtoActual = itemService.addCommentDto(commentDto, userOwnerId, itemForCommentId);

        Assertions.assertEquals(commentDto.getId(), commentDtoActual.getId());
        Assertions.assertEquals(commentDto.getText(), commentDtoActual.getText());
        Assertions.assertEquals(commentDto.getAuthorName(), commentDtoActual.getAuthorName());
        Assertions.assertEquals(commentDto.getItemId(), commentDtoActual.getItemId());
        Assertions.assertEquals(commentDto.getCreated(), commentDtoActual.getCreated());

        verify(itemRepository).findById(userOwnerId);
        verify(userService).getUser(userOwnerId);
        verify(bookingService).findBookingForComments(userOwnerId, itemForCommentId);
        verify(commentRepository).save(CommentMapper.toComment(commentDto, expectedUser, expectedItem));

    }

    @Test
    void addComment_whenUserNotBookingItem_thenReturnedValidationException() {
        int userOwnerId = 1;
        int itemForCommentId = 1;
        Item expectedItem = new Item(1, "test", "testDescription", true, userOwnerId);
        User expectedUser = new User(1, "Test", "test@email");
        CommentDto commentDto = new CommentDto(1, "test", "Test", itemForCommentId
                , LocalDateTime.now());

        Mockito.when(itemRepository.findById(userOwnerId)).thenReturn(Optional.of(expectedItem));
        Mockito.when(userService.getUser(userOwnerId)).thenReturn(UserMapper.toUserDto(expectedUser));
        Mockito.when(bookingService.findBookingForComments(userOwnerId, itemForCommentId)).thenReturn(0);

        Assertions.assertThrows(ValidationException.class, () -> itemService.addCommentDto(commentDto
                , userOwnerId, itemForCommentId));
        verify(commentRepository, never()).save(CommentMapper.toComment(commentDto, expectedUser, expectedItem));
    }


    @Test
    void getItemWithRequest() {
        int requestId = 1;
        int userOwnerId = 1;
        Item expectedItem = new Item(1, "test", "testDescription", true, userOwnerId
                , requestId);
        List<Item> expectedItemList = new ArrayList<>();
        expectedItemList.add(expectedItem);

        Mockito.when(itemRepository.getItemWithRequest(requestId)).thenReturn(expectedItemList);
        List<Item> actualItemList = itemService.getItemWithRequest(requestId);

        verify(itemRepository).getItemWithRequest(requestId);
        Assertions.assertEquals(expectedItemList.size(), actualItemList.size());
        Assertions.assertEquals(expectedItemList.get(0).getId(), actualItemList.get(0).getId());
        Assertions.assertEquals(expectedItemList.get(0).getName(), actualItemList.get(0).getName());
        Assertions.assertEquals(expectedItemList.get(0).getDescription(), actualItemList.get(0).getDescription());
        Assertions.assertEquals(expectedItemList.get(0).getAvailable(), actualItemList.get(0).getAvailable());
        Assertions.assertEquals(expectedItemList.get(0).getUserId(), actualItemList.get(0).getUserId());
        Assertions.assertEquals(expectedItemList.get(0).getRequestId(), actualItemList.get(0).getRequestId());
    }


}
