package ru.practicum.shareit.booking.web.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.web.dto.ItemDto;
import ru.practicum.shareit.user.web.dto.UserDto;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDtoNoBookingDate {
    int id;
    LocalDateTime start;
    LocalDateTime end;
    UserDto booker;
    ItemDto item;
    String status;

    public BookingDtoNoBookingDate(int id, UserDto booker, ItemDto item, String status) {
        this.id = id;
        this.booker = booker;
        this.item = item;
        this.status = status;
    }
}
