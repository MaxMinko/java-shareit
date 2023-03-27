package ru.practicum.shareit;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.request.ItemRequestService;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.UserService;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ItemControllerTest {
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
    void getAllUserItems() {
        int userId=0;
        mockMvc.perform(get("/items").header("X-Sharer-User-Id",userId))
                .andDo(print())
                .andExpect(status().isOk());
        verify(itemService).getAllUserItems(userId);
    }
    @SneakyThrows
    @Test
    void getItem() {
        int itemId=0;
        int userId=0;
        mockMvc.perform(get("/items/{itemId}",itemId).header("X-Sharer-User-Id",userId))
                .andDo(print())
                .andExpect(status().isOk());
        verify(itemService).getItemDto(itemId,userId);
    }

    @SneakyThrows
    @Test
    void addComment() {
        int itemId=1;
        int userId=1;
        CommentDto commentDto=new CommentDto(1,"test","test",1);
        mockMvc.perform(post("/items/{itemId}/comment",itemId)
                        .header("X-Sharer-User-Id",userId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(commentDto)))
                .andDo(print())
                .andExpect(status().isOk());
        verify(itemService).addCommentDto(commentDto,userId,itemId);
    }
    @SneakyThrows
    @Test
    void addComment_thenCommentNotValid_whenReturnedException(){
        int itemId=1;
        int userId=1;
        CommentDto commentDto=new CommentDto(1,"","test",1);
        mockMvc.perform(post("/items/{itemId}/comment",itemId)
                        .header("X-Sharer-User-Id",userId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(commentDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());
        verify(itemService,never()).addCommentDto(commentDto,userId,itemId);
    }


}
