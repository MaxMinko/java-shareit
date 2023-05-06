package ru.practicum.shareit.request.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.web.dto.ItemRequestDto;
import ru.practicum.shareit.request.web.dto.ItemRequestDtoForResponse;
import ru.practicum.shareit.request.service.ItemRequestService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping(path = "/requests")
public class ItemRequestController {
    private final ItemRequestService itemRequestService;
    @PostMapping()
    public ItemRequestDto addRequest(@RequestHeader("X-Sharer-User-Id") int userId,
                                     @Valid @RequestBody ItemRequestDto itemRequestDto) {
        return itemRequestService.addRequest(itemRequestDto,userId);
    }
    @GetMapping()
    public List<ItemRequestDtoForResponse> getAllUserItemRequest(@RequestHeader("X-Sharer-User-Id") int userId) {
        return itemRequestService.getAllUserItemRequest(userId);
    }
    @GetMapping("/{requestId}")
    public ItemRequestDtoForResponse getItemRequest(@RequestHeader("X-Sharer-User-Id") int userId,
                                                    @PathVariable("requestId") int requestId) {
        return itemRequestService.getItemRequest(userId,requestId);
    }

    @GetMapping("/all")
    public List<ItemRequestDtoForResponse> getListWithOtherUserRequest(@RequestHeader("X-Sharer-User-Id") int userId,
       @RequestParam(value = "from")  int from,
       @RequestParam(value = "size")  int size ) {
        return itemRequestService.getListWithOtherUserRequest(userId,from,size);
    }
}
