package ru.practicum.shareit.request.web.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemRequestDto {
    Integer id;
    @NotEmpty()
    String description;
    LocalDateTime created;


    public ItemRequestDto(String description) {
        this.description = description;
    }

    public ItemRequestDto(Integer id, String description, LocalDateTime created) {
        this.id = id;
        this.description = description;
        this.created = created;
    }
}
