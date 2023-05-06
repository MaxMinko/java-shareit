package ru.practicum.shareit.request.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.client.ItemRequestClient;
import ru.practicum.shareit.request.web.dto.ItemRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@RestController
@RequestMapping(value = "/requests", consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestClient itemRequestClient;

    @PostMapping()
    public ResponseEntity<Object> addRequest(@RequestHeader("X-Sharer-User-Id") int userId,
                                             @Valid @RequestBody ItemRequestDto itemRequestDto) {
        return itemRequestClient.addRequest(userId, itemRequestDto);
    }

    @GetMapping()
    public ResponseEntity<Object> getAllUserItemRequest(@RequestHeader("X-Sharer-User-Id") int userId) {
        return itemRequestClient.getAllUserItemRequest(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getItemRequest(@RequestHeader("X-Sharer-User-Id") int userId,
                                                 @PathVariable("requestId") int requestId) {
        return itemRequestClient.getItemRequest(userId, requestId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getListWithOtherUserRequest(@RequestHeader("X-Sharer-User-Id") int userId,
                                                              @RequestParam(value = "from", defaultValue = "0") @Min(0) Integer from,
                                                              @RequestParam(value = "size", defaultValue = "1") @Min(1) @Max(100) Integer size) {
        return itemRequestClient.getListWithOtherUserRequest(userId, from, size);
    }
}
