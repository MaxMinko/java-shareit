package ru.practicum.shareit.booking.web.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDtoWithIdAndBookerId {
    int id;
    int bookerId;

   public BookingDtoWithIdAndBookerId(){

   }
}
