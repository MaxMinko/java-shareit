package ru.practicum.shareit.request.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.dto.ItemDtoForRequest;

import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemRequestDtoForResponse {
    int id;
    String description;
    LocalDateTime created;
    List<ItemDtoForRequest> items;

    public ItemRequestDtoForResponse(int id, String description, LocalDateTime created) {
        this.id = id;
        this.description = description;
        this.created = created;
    }
    public ItemRequestDtoForResponse(int id, String description, LocalDateTime created,List<ItemDtoForRequest>items) {
        this.id = id;
        this.description = description;
        this.created = created;
        this.items=items;
    }
}
