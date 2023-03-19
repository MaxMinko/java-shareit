package ru.practicum.shareit.item;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.UserController;


import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final UserController userController;

    @PostMapping()
    public ItemDto addItem(@RequestHeader("X-Sharer-User-Id") int userId,
                           @Valid @RequestBody ItemDto itemDto) {
        return itemService.addItem(itemDto, userController.getUser(userId).getId());
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestHeader("X-Sharer-User-Id") int userId,
                              @PathVariable("itemId") int itemId,
                              @RequestBody ItemDto itemDto) {
        userController.getUser(userId);
        return itemService.updateItem(itemDto, userId, itemId);
    }

    @GetMapping()
    public List<ItemDto> getAllUserItems(@RequestHeader("X-Sharer-User-Id") int userId) {
        return itemService.getAllUserItems(userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItem(@RequestHeader("X-Sharer-User-Id") int userId,
                           @PathVariable("itemId") int itemId) {
        return itemService.getItemDto(itemId, userId);
    }

    @GetMapping("/search")
    public List<ItemDto> getItemByDescription(@RequestParam(value = "text", required = true) String text) {
        return itemService.getItemByDescription(text);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@RequestHeader("X-Sharer-User-Id") int userId,
                                 @PathVariable("itemId") int itemId,
                                 @Valid @RequestBody CommentDto commentDto) {
        return itemService.addCommentDto(commentDto, userId, itemId);
    }
}
