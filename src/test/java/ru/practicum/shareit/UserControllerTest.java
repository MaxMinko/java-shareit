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
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserDto;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class UserControllerTest {
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


    @SneakyThrows
    @Test
    void getUser() {
        int userId = 0;
        mockMvc.perform(get("/users/{userId}", userId))
                .andExpect(status().isOk());

        verify(userService).getUser(userId);
    }

    @SneakyThrows
    @Test
    void addUser() {
        UserDto userDto = new UserDto(1, "test", "test");
        Mockito.when(userService.addUser(userDto)).thenReturn(userDto);

        String result = mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        Assertions.assertEquals(objectMapper.writeValueAsString(userDto), result);

        verify(userService).addUser(userDto);
    }

    @SneakyThrows
    @Test
    void deleteUser() {
        int userId = 0;
        mockMvc.perform(delete("/users/{userId}", userId))
                .andExpect(status().isOk());

        verify(userService).userDelete(userId);
    }

    @SneakyThrows
    @Test
    void getAllUser() {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());

        verify(userService).getAllUsers();
    }


}
