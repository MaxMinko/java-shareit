package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoForResponse;
import ru.practicum.shareit.booking.model.Booking;


import java.util.List;


@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping()
    public BookingDtoForResponse addBooking(@RequestHeader("X-Sharer-User-Id") int userId,
                                            @RequestBody BookingDto bookingDto) {
        return bookingService.addBooking(bookingDto, userId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoForResponse approvedBooking(@RequestHeader("X-Sharer-User-Id") int userId,
                                                 @RequestParam(value = "approved", required = true) Boolean status,
                                                 @PathVariable int bookingId) {
        return bookingService.approvedBooking(status, userId, bookingId);
    }

    @GetMapping()
    public List<BookingDtoForResponse> getBooking(@RequestHeader("X-Sharer-User-Id") int userId,
                                                  @RequestParam(value = "state", required = false,
                                                          defaultValue = "ALL") String state) {
        return bookingService.getBookingWithState(userId, state);
    }

    @GetMapping("/{bookingId}")
    public BookingDtoForResponse getBooking(@RequestHeader("X-Sharer-User-Id") int userId,
                                            @PathVariable int bookingId) {
        return bookingService.getBooking(userId, bookingId);
    }

    @GetMapping("/owner")
    public List<BookingDtoForResponse> getBookingOwner(@RequestHeader("X-Sharer-User-Id") int userId,
                                                       @RequestParam(value = "state", required = false
                                                               , defaultValue = "ALL") String state) {
        return bookingService.getBookingOwner(userId, state);
    }

}
