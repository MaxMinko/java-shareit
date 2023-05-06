package ru.practicum.shareit;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.request.ItemRequestService;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.UserService;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ItemRequestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;
    @MockBean
    private BookingService bookingService;
    @MockBean
    private ItemService itemService;
    @MockBean
    private ItemRequestService itemRequestService;
    @MockBean
    private UserController userController;

    @SneakyThrows
    @Test
    void getItemRequest() {
        int requestId = 0;
        int userId = 0;
        mockMvc.perform(get("/requests/{requestId}", requestId).header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk());

        verify(itemRequestService).getItemRequest(userId, requestId);
    }

    @SneakyThrows
    @Test
    void getAllUserItemRequest() {

        int userId = 0;
        mockMvc.perform(get("/requests").header("X-Sharer-User-Id", userId))
                .andExpect(status().isOk());

        verify(itemRequestService).getAllUserItemRequest(userId);
    }

    @SneakyThrows
    @Test
    void addRequest() {
        int userId = 0;
        ItemRequestDto itemRequestDto = new ItemRequestDto("test");

        Mockito.when(itemRequestService.addRequest(itemRequestDto, userId)).thenReturn(itemRequestDto);
        String result = mockMvc.perform(post("/requests")
                        .header("X-Sharer-User-Id", userId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(itemRequestDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertEquals(objectMapper.writeValueAsString(itemRequestDto), result);

        verify(itemRequestService).addRequest(itemRequestDto, userId);
    }

    @SneakyThrows
    @Test
    void addRequest_thenItemRequestNotValid_whenReturnedException() {
        int userId = 0;
        ItemRequestDto itemRequestDto = new ItemRequestDto("");

        mockMvc.perform(post("/requests")
                        .header("X-Sharer-User-Id", userId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(itemRequestDto)))
                .andExpect(status().isBadRequest());

        verify(itemRequestService, never()).addRequest(itemRequestDto, userId);
    }

}
