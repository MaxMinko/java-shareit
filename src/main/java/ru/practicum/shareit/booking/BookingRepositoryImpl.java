package ru.practicum.shareit.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;


public class BookingRepositoryImpl implements BookingRepositoryCustom {
    BookingRepository bookingRepository;

    @Autowired
    public BookingRepositoryImpl(@Lazy BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }


}
