package ru.practicum.shareit.booking.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;


import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDto {
    int id;
    LocalDateTime start;
    LocalDateTime end;
    int bookerId;
    int itemId;
    String status;

    public BookingDto(int id, LocalDateTime start, LocalDateTime end, int bookerId, int itemId, String status) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.bookerId = bookerId;
        this.itemId = itemId;
        this.status = status;
    }

public BookingDto(){

}

}

