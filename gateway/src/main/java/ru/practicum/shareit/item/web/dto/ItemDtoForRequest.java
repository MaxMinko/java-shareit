package ru.practicum.shareit.item.web.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ItemDtoForRequest {
    int id;
    @NotEmpty()
    String name;
    @NotEmpty()
    String description;
    @NotNull()
    Boolean available;
    @NotNull()
    int requestId;
}
