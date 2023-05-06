package ru.practicum.shareit.request;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoForResponse;

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
       @RequestParam(value = "from",defaultValue = "0") @Min(0) int from,
       @RequestParam(value = "size",defaultValue = "1") @Min(1) @Max(100) int size ) {
        return itemRequestService.getListWithOtherUserRequest(userId,from,size);
    }
}
