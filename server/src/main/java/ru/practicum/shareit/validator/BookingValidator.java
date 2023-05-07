package ru.practicum.shareit.validator;


import ru.practicum.shareit.booking.web.dto.BookingDto;
import ru.practicum.shareit.exception.ValidationException;

import java.time.LocalDateTime;

public class BookingValidator {

    public void checkBookingDto(BookingDto bookingDto) {
        if (bookingDto.getStart() == null) {
            throw new ValidationException("Время старта не задано.");
        }
        if (bookingDto.getEnd() == null) {
            throw new ValidationException("Время конца не задано.");
        }
        if (bookingDto.getStart().isEqual(bookingDto.getEnd())) {
            throw new ValidationException("Дата старта и конца не может быть одинаковой.");
        }
        if (bookingDto.getStart().isAfter(bookingDto.getEnd())) {
            throw new ValidationException("Дата старта не может быть после даты конца бронирования.");
        }
        if (bookingDto.getStart().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Дата страта не может быть в прошлом.");
        }
        if (bookingDto.getEnd().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Дата окончания бронирования не может быть в прошлом.");
        }
    }


}
