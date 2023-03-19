package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface BookingService {
    Booking addBooking(BookingDto bookingDto, int userId);

    Booking getBooking(int userId, int bookingId);

    Booking approvedBooking(Boolean status, int userId, int bookingId);

    List<Booking> getBookingWithState(int userId, String state);

    List<Booking> getBookingOwner(int userId, String state);

    Booking findNextBookingForItem(int itemId);

    Booking findLastBookingForItem(int itemId);

    Integer findBookingForComments(int userId, int itemId);
}
