package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.booking.dto.BookingDtoWithIdAndBookerId;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ItemDto {
    int id;
    @NotEmpty()
    String name;
    @NotEmpty()
    String description;
    @NotNull()
    Boolean available;

    Integer requestId;

    BookingDtoWithIdAndBookerId lastBooking;

    BookingDtoWithIdAndBookerId nextBooking;

    List<CommentDto> comments;


    public ItemDto(int id, String name, String description, Boolean available, Integer requestId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.requestId = requestId;
    }

}

