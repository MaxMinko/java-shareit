package ru.practicum.shareit.booking.web.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.db.model.Item;
import ru.practicum.shareit.user.db.model.User;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDtoForResponse {
    int id;
    LocalDateTime start;
    LocalDateTime end;
    User booker;
    Item item;
    String status;

    public BookingDtoForResponse(int id, LocalDateTime start, LocalDateTime end, User booker, Item item, String status) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.booker = booker;
        this.item = item;
        this.status = status;
    }

    public BookingDtoForResponse() {

    }
}
