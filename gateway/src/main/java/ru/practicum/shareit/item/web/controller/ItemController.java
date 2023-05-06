package ru.practicum.shareit.item.web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.client.ItemClient;
import ru.practicum.shareit.item.web.dto.CommentDto;
import ru.practicum.shareit.item.web.dto.ItemDto;
import ru.practicum.shareit.user.client.UserClient;

import javax.validation.Valid;


@RestController
@RequestMapping(  value = "/items", consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ItemController {
private final ItemClient itemClient;
private final UserClient userClient;



    @PostMapping()
    public ResponseEntity<Object> addItem(@RequestHeader("X-Sharer-User-Id") int userId,
                                          @Valid @RequestBody ItemDto itemDto) {
        userClient.getUser(userId);
        return itemClient.addItem(userId,itemDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@RequestHeader("X-Sharer-User-Id") int userId,
                              @PathVariable("itemId") int itemId,
                              @RequestBody ItemDto itemDto) {
        userClient.getUser(userId);
        return itemClient.updateItem(userId,itemId,itemDto);
    }

    @GetMapping()
    public ResponseEntity<Object> getAllUserItems(@RequestHeader("X-Sharer-User-Id") int userId) {
        return itemClient.getAllUserItems(userId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItem(@RequestHeader("X-Sharer-User-Id") int userId,
                           @PathVariable("itemId") int itemId) {
        return itemClient.getItem(userId,itemId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> getItemByDescription(@RequestParam(value = "text") String text) {
        return itemClient.getItemByDescription(text);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestHeader("X-Sharer-User-Id") int userId,
                                 @PathVariable("itemId") int itemId,
                                 @Valid @RequestBody CommentDto commentDto) {
        return itemClient.addComment(userId,itemId,commentDto);
    }
}
