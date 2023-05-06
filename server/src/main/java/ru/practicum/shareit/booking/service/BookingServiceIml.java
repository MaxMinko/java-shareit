package ru.practicum.shareit.booking.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.web.mapper.BookingMapper;
import ru.practicum.shareit.booking.db.model.BookingStatus;
import ru.practicum.shareit.booking.db.repository.BookingRepository;
import ru.practicum.shareit.booking.web.dto.BookingDto;
import ru.practicum.shareit.booking.web.dto.BookingDtoForResponse;
import ru.practicum.shareit.booking.db.model.Booking;
import ru.practicum.shareit.exception.BookingNotFoundException;
import ru.practicum.shareit.exception.ItemUnavailableException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.item.db.model.Item;
import ru.practicum.shareit.user.web.mapper.UserMapper;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.db.model.User;
import ru.practicum.shareit.validator.BookingValidator;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceIml implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemService itemService;
    private final BookingValidator bookingValidator;
    private final UserService userService;

    @Autowired
    public BookingServiceIml(BookingRepository bookingRepository, @Lazy ItemService itemService,
                             UserService userService) {
        this.bookingRepository = bookingRepository;
        this.itemService = itemService;
        this.userService = userService;
        bookingValidator = new BookingValidator();
    }

    @Transactional
    @Override
    public BookingDtoForResponse addBooking(BookingDto bookingDto, int userId) {
        User user = UserMapper.toUser(userService.getUser(userId));
        Item item = itemService.getItem(bookingDto.getItemId());
        bookingValidator.checkBookingDto(bookingDto);
        if (item.getAvailable() == true) {
            if (userId == item.getUserId()) {
                throw new BookingNotFoundException("Владелец вещи не может ее забронировать.");
            }
            Booking booking = new Booking();
            booking.setStart(bookingDto.getStart());
            booking.setEnd(bookingDto.getEnd());
            booking.setItem(item);
            booking.setBooker(user);
            booking.setStatus(String.valueOf(BookingStatus.WAITING));
            return BookingMapper.toBookingDtoForResponse(bookingRepository.save(booking));
        } else {
            throw new ItemUnavailableException("Вещь не доступна для бронирования.");
        }
    }

    @Override
    public BookingDtoForResponse getBooking(int userId, int bookingId) {
        Booking booking = bookingRepository.findBooking(bookingId, userId);
        if (booking == null) {
            throw new BookingNotFoundException("Бронирование не найдено.");
        }
        return BookingMapper.toBookingDtoForResponse(booking);

    }

    @Transactional
    @Override
    public BookingDtoForResponse approvedBooking(Boolean status, int userId, int bookingId) {
        Booking booking = bookingRepository.findBookingForApprove(bookingId, userId);
        if (booking == null) {
            throw new BookingNotFoundException("Бронирование не найдено");
        }
        if (booking.getStatus().equals("APPROVED")) {
            throw new ValidationException("Бронирование уже подтверждено.");
        }
        if (status == true) {
            booking.setStatus(String.valueOf(BookingStatus.APPROVED));
            return BookingMapper.toBookingDtoForResponse(bookingRepository.save(booking));
        } else {
            booking.setStatus(String.valueOf(BookingStatus.REJECTED));
            return BookingMapper.toBookingDtoForResponse(bookingRepository.save(booking));
        }
    }

    @Override
    public List<BookingDtoForResponse> getBookingWithState(int userId, String state, int from, int size) {
        userService.getUser(userId);
        if (from == 0 && size == 0) {
            throw new ValidationException("");
        }
        switch (state) {
            case ("ALL"):
                return bookingRepository.findByBookerIdOrderByStartDesc(userId, from, size).stream()
                        .map(BookingMapper::toBookingDtoForResponse).collect(Collectors.toList());
            case ("CURRENT"):
                return bookingRepository.findBookingWithCurrentStatus(userId, from, size).stream()
                        .map(BookingMapper::toBookingDtoForResponse).collect(Collectors.toList());
            case ("FUTURE"):
                return bookingRepository.findBookingFuture(userId, from, size).stream()
                        .map(BookingMapper::toBookingDtoForResponse).collect(Collectors.toList());
            case ("WAITING"):
                return bookingRepository.findBookingWithWaitingStatus(userId, from, size).stream()
                        .map(BookingMapper::toBookingDtoForResponse).collect(Collectors.toList());
            case ("REJECTED"):
                return bookingRepository.findBookingWithRejectedStatus(userId, from, size).stream()
                        .map(BookingMapper::toBookingDtoForResponse).collect(Collectors.toList());
            case ("PAST"):
                return bookingRepository.findBookingInPast(userId, from, size).stream()
                        .map(BookingMapper::toBookingDtoForResponse).collect(Collectors.toList());
            default:
                throw new ValidationException("Unknown state: UNSUPPORTED_STATUS");
        }
    }

    @Override
    public List<BookingDtoForResponse> getBookingOwner(int userId, String state, int from, int size) {
        userService.getUser(userId);
        if (from == 0 && size == 0) {
            throw new ValidationException("");
        }
        switch (state) {
            case ("ALL"):
                return bookingRepository.findByItem_userIdOrderByStartDesc(userId, from, size).stream()
                        .map(BookingMapper::toBookingDtoForResponse).collect(Collectors.toList());
            case ("CURRENT"):
                return bookingRepository.findCurrentBookingByOwner(userId, from, size).stream()
                        .map(BookingMapper::toBookingDtoForResponse).collect(Collectors.toList());
            case ("FUTURE"):
                return bookingRepository.findBookingFutureByOwner(userId, from, size).stream()
                        .map(BookingMapper::toBookingDtoForResponse).collect(Collectors.toList());
            case ("WAITING"):
                return bookingRepository.findWaitingBookingByOwner(userId, from, size).stream()
                        .map(BookingMapper::toBookingDtoForResponse).collect(Collectors.toList());
            case ("REJECTED"):
                return bookingRepository.findRejectedBookingByOwner(userId, from, size).stream()
                        .map(BookingMapper::toBookingDtoForResponse).collect(Collectors.toList());
            case ("PAST"):
                return bookingRepository.findBookingInPastByOwner(userId, from, size).stream()
                        .map(BookingMapper::toBookingDtoForResponse).collect(Collectors.toList());
            default:
                throw new ValidationException("Unknown state: UNSUPPORTED_STATUS");
        }
    }

    @Override
    public BookingDtoForResponse findNextBookingForItem(int itemId) {
        return BookingMapper.toBookingDtoForResponse(bookingRepository.findNextBookingForItem(itemId));
    }

    @Override
    public BookingDtoForResponse findLastBookingForItem(int itemId) {
        return BookingMapper.toBookingDtoForResponse(bookingRepository.findLastBookingForItem(itemId));
    }

    @Override
    public Integer findBookingForComments(int userId, int itemId) {
        return bookingRepository.findBookingForComments(userId, itemId);
    }
}

