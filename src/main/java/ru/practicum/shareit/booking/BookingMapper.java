package ru.practicum.shareit.booking;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.BookingDtoForApprove;
import ru.practicum.shareit.booking.dto.BookingDtoNoBookingDate;
import ru.practicum.shareit.booking.dto.BookingDtoWithIdAndBookerId;
import ru.practicum.shareit.booking.model.Booking;


@Component
public class BookingMapper {

    public static BookingDtoForApprove bookingDtoForApprove(Booking booking) {
        return new BookingDtoForApprove(booking.getId(), booking.getBooker(), booking.getItem(), booking.getStatus());
    }

    public static BookingDtoWithIdAndBookerId toBookingDtoWithIdAndBookerId(Booking booking) {
        return new BookingDtoWithIdAndBookerId(booking.getId(), booking.getBooker().getId());
    }

    public static BookingDtoNoBookingDate toBookingDtoNoBookingDate(Booking booking) {
        return new BookingDtoNoBookingDate(booking.getId(), booking.getBooker(), booking.getItem()
                , booking.getStatus());
    }
}
