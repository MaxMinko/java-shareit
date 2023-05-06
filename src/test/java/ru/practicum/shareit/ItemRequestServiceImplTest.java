package ru.practicum.shareit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestRepository;
import ru.practicum.shareit.request.ItemRequestServiceImpl;
import ru.practicum.shareit.request.dto.ItemRequestDtoForResponse;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ItemRequestServiceImplTest {
    @Mock
    ItemService itemService;
    @Mock
    ItemRequestRepository itemRequestRepository;
    @Mock
    UserService userService;

    @InjectMocks
    ItemRequestServiceImpl itemRequestService;


    @Test
    void getItemRequest_whenUserAndRequestFound_thenReturnedRequest() {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setId(1);
        itemRequest.setUserId(1);
        itemRequest.setDescription("test");
        itemRequest.setCreated(LocalDateTime.of(2023, 3, 27, 4, 43, 34));

        Mockito.when(userService.getUser(1)).thenReturn(new UserDto(1, "test", "test"));
        Mockito.when(itemRequestRepository.findById(1)).thenReturn(Optional.of(itemRequest));


        ItemRequestDtoForResponse actualItemRequest = itemRequestService.getItemRequest(1, 1);

        Assertions.assertEquals(itemRequest.getId(), actualItemRequest.getId());
        Assertions.assertEquals(itemRequest.getCreated(), actualItemRequest.getCreated());
        Assertions.assertEquals(itemRequest.getDescription(), actualItemRequest.getDescription());
    }

    @Test
    void getItemRequest_whenUserFoundAndRequestNotFound_thenUserNotFoundException() {
        Mockito.when(userService.getUser(1)).thenReturn(new UserDto(1, "test", "test"));
        Mockito.when(itemRequestRepository.findById(1)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> itemRequestService
                .getItemRequest(1, 1));

    }


}
