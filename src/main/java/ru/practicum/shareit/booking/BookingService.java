package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoForResponse;
import ru.practicum.shareit.booking.model.Booking;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface BookingService {
    BookingDtoForResponse addBooking(BookingDto bookingDto, int userId);

    BookingDtoForResponse getBooking(int userId, int bookingId);

    BookingDtoForResponse approvedBooking(Boolean status, int userId, int bookingId);

    List<BookingDtoForResponse> getBookingWithState(int userId, String state);

    List<BookingDtoForResponse> getBookingOwner(int userId, String state);

    BookingDtoForResponse findNextBookingForItem(int itemId);

    BookingDtoForResponse findLastBookingForItem(int itemId);

    Integer findBookingForComments(int userId, int itemId);
}
