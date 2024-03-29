package ru.practicum.shareit.booking;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.dto.*;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;


@Component
public class BookingMapper {

    public static BookingDtoForApprove toBookingDtoForApprove(Booking booking) {
        return new BookingDtoForApprove(booking.getId(), booking.getBooker(), booking.getItem(), booking.getStatus());
    }

    public static BookingDtoWithIdAndBookerId toBookingDtoWithIdAndBookerId(BookingDtoForResponse booking) {
        if(booking.getId()!=0){
            return new BookingDtoWithIdAndBookerId(booking.getId(), booking.getBooker().getId());
        }else {
            return new BookingDtoWithIdAndBookerId();
        }

    }

    public static BookingDtoNoBookingDate toBookingDtoNoBookingDate(Booking booking) {
        return new BookingDtoNoBookingDate(booking.getId(), booking.getBooker(), booking.getItem()
                , booking.getStatus());
    }

    public static BookingDtoForResponse toBookingDtoForResponse(Booking booking) {
        BookingDtoForResponse bookingDtoForResponse;
        if(booking!=null) {
            bookingDtoForResponse = new BookingDtoForResponse(booking.getId()
                    , booking.getStart(), booking.getEnd(), new User(), new Item(), booking.getStatus());
            bookingDtoForResponse.getBooker().setId(booking.getBooker().getId());
            bookingDtoForResponse.getItem().setId(booking.getItem().getId());
            bookingDtoForResponse.getItem().setName(booking.getItem().getName());
        }else {
            bookingDtoForResponse=new BookingDtoForResponse();
        }
        return bookingDtoForResponse;
    }
}
