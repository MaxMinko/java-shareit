package ru.practicum.shareit.booking.web.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.web.dto.ItemDto;
import ru.practicum.shareit.user.web.dto.UserDto;


@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDtoForApprove {
    int id;
    UserDto booker;
    ItemDto item;
    String status;
}
