package ru.practicum.shareit.booking.web.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.db.model.Item;
import ru.practicum.shareit.user.db.model.User;


@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDtoForApprove {
    int id;
    User booker;
    Item item;
    String status;
}
