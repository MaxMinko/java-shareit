package ru.practicum.shareit.booking.web.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.db.model.Item;
import ru.practicum.shareit.user.db.model.User;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDtoNoBookingDate {
    int id;
    LocalDateTime start;
    LocalDateTime end;
    User booker;
    Item item;
    String status;

    public BookingDtoNoBookingDate(int id, User booker, Item item, String status) {
        this.id = id;
        this.booker = booker;
        this.item = item;
        this.status = status;
    }
}
