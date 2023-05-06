package ru.practicum.shareit.booking.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.client.BookingClient;
import ru.practicum.shareit.booking.web.dto.BookingDto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@RestController
@RequestMapping(value = "/bookings", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class BookingController {
    private final BookingClient bookingClient;

    @PostMapping()
    public ResponseEntity<Object> addBooking(@RequestHeader("X-Sharer-User-Id") int userId, @RequestBody BookingDto bookingDto) {
        return bookingClient.addBooking(userId, bookingDto);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> approvedBooking(@RequestHeader("X-Sharer-User-Id") int userId, @RequestParam(value = "approved") Boolean status, @PathVariable int bookingId) {
        return bookingClient.approvedBooking(userId, status, bookingId);
    }

    @GetMapping()
    public ResponseEntity<Object> getBooking(@RequestHeader("X-Sharer-User-Id") int userId, @RequestParam(value = "state", defaultValue = "ALL") String state, @RequestParam(value = "from", defaultValue = "0") @Min(0) Integer from, @RequestParam(value = "size", defaultValue = "100") @Min(1) @Max(100) Integer size) {
        return bookingClient.getBooking(userId, state, from, size);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBooking(@RequestHeader("X-Sharer-User-Id") int userId, @PathVariable int bookingId) {
        return bookingClient.getBooking(userId, bookingId);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getBookingOwner(@RequestHeader("X-Sharer-User-Id") int userId, @RequestParam(value = "state", defaultValue = "ALL") String state, @RequestParam(value = "from", defaultValue = "0") @Min(0) Integer from, @RequestParam(value = "size", defaultValue = "100") @Min(1) @Max(100) Integer size) {
        return bookingClient.getBookingOwner(userId, state, from, size);
    }

}
