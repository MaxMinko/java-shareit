package ru.practicum.shareit.booking;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.BookingNotFoundException;
import ru.practicum.shareit.exception.ItemUnavailableException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.validator.BookingValidator;


import java.util.List;

@Service
public class BookingServiceIml implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemService itemService;
    private final BookingValidator bookingValidator;
    private final UserService userService;

    @Autowired
    public BookingServiceIml(BookingRepository bookingRepository, @Lazy ItemService itemService, UserService userService) {
        this.bookingRepository = bookingRepository;
        this.itemService = itemService;
        this.userService = userService;
        bookingValidator = new BookingValidator();
    }


    @Override
    public Booking addBooking(BookingDto bookingDto, int userId) {
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
            return bookingRepository.save(booking);
        } else {
            throw new ItemUnavailableException("Вещь не доступна для бронирования.");
        }
    }

    @Override
    public Booking getBooking(int userId, int bookingId) {
        Booking booking = bookingRepository.findBooking(bookingId, userId);
        if (booking == null) {
            throw new BookingNotFoundException("Бронирование не найдено.");
        }
        return booking;

    }

    @Override
    public Booking approvedBooking(Boolean status, int userId, int bookingId) {
        Booking booking = bookingRepository.findBookingForApprove(bookingId, userId);
        if (booking == null) {
            throw new BookingNotFoundException("Бронирование не найдено");
        }
        if (booking.getStatus().equals("APPROVED")) {
            throw new ValidationException("Бронирование уже подтверждено.");
        }
        if (status == true) {
            booking.setStatus(String.valueOf(BookingStatus.APPROVED));
            return bookingRepository.save(booking);
        } else {
            booking.setStatus(String.valueOf(BookingStatus.REJECTED));
            return bookingRepository.save(booking);
        }
    }

    @Override
    public List<Booking> getBookingWithState(int userId, String state) {
        userService.getUser(userId);
        switch (state) {
            case ("ALL"):
                return bookingRepository.findByBookerIdOrderByStartDesc(userId);
            case ("CURRENT"):
                return bookingRepository.findBookingWithCurrentStatus(userId);
            case ("FUTURE"):
                return bookingRepository.findBookingFuture(userId);
            case ("WAITING"):
                return bookingRepository.findBookingWithWaitingStatus(userId);
            case ("REJECTED"):
                return bookingRepository.findBookingWithRejectedStatus(userId);
            case ("PAST"):
                return bookingRepository.findBookingInPast(userId);
            default:
                throw new ValidationException("Unknown state: UNSUPPORTED_STATUS");
        }
    }

    @Override
    public List<Booking> getBookingOwner(int userId, String state) {
        userService.getUser(userId);
        switch (state) {
            case ("ALL"):
                return bookingRepository.findByItem_userIdOrderByStartDesc(userId);
            case ("CURRENT"):
                return bookingRepository.findCurrentBookingByOwner(userId);
            case ("FUTURE"):
                return bookingRepository.findBookingFutureByOwner(userId);
            case ("WAITING"):
                return bookingRepository.findWaitingBookingByOwner(userId);
            case ("REJECTED"):
                return bookingRepository.findRejectedBookingByOwner(userId);
            case ("PAST"):
                return bookingRepository.findBookingInPastByOwner(userId);
            default:
                throw new ValidationException("Unknown state: UNSUPPORTED_STATUS");
        }
    }

    @Override
    public Booking findNextBookingForItem(int itemId) {
        return bookingRepository.findNextBookingForItem(itemId);
    }

    @Override
    public Booking findLastBookingForItem(int itemId) {
        return bookingRepository.findLastBookingForItem(itemId);
    }

    @Override
    public Integer findBookingForComments(int userId, int itemId) {
        return bookingRepository.findBookingForComments(userId, itemId);
    }
}

