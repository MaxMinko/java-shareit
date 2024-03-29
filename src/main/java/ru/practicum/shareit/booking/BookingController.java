package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoForResponse;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
                                                 @RequestParam(value = "approved") Boolean status,
                                                 @PathVariable int bookingId) {
        return bookingService.approvedBooking(status, userId, bookingId);
    }

    @GetMapping()
    public List<BookingDtoForResponse> getBooking(@RequestHeader("X-Sharer-User-Id") int userId,
                                                @RequestParam(value = "state", defaultValue = "ALL") String state,
                                                @RequestParam(value = "from",defaultValue = "0") @Min(0) int from,
                                                @RequestParam(value = "size",defaultValue = "100") @Min(1) @Max(100)
                                                      int size) {
        return bookingService.getBookingWithState(userId, state, from, size);
    }

    @GetMapping("/{bookingId}")
    public BookingDtoForResponse getBooking(@RequestHeader("X-Sharer-User-Id") int userId,
                                            @PathVariable int bookingId) {
        return bookingService.getBooking(userId, bookingId);
    }

    @GetMapping("/owner")
    public List<BookingDtoForResponse> getBookingOwner(@RequestHeader("X-Sharer-User-Id") int userId,
                                                       @RequestParam(value = "state", defaultValue = "ALL")
                                                       String state,
                                                      @RequestParam(value = "from",defaultValue = "0") @Min(0) int from,
                                                      @RequestParam(value = "size",defaultValue = "100")
                                                      @Min(1) @Max(100) int size) {
        return bookingService.getBookingOwner(userId, state, from, size);
    }

}
