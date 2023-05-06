package ru.practicum.shareit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.BookingServiceIml;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoForResponse;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.exception.BookingNotFoundException;
import ru.practicum.shareit.exception.ItemUnavailableException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.validator.BookingValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookingServiceImplTest {
    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private ItemService itemService;
    @Mock
    private BookingValidator bookingValidator;
    @Mock
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    BookingServiceIml bookingService;


    @Test
    void addBooking_whenBookingValid_thenReturnedSavedBooking() {
        int userBookerId = 2;
        int userOwnerId = 1;
        int itemForBooking = 1;
        Item expectedItem = new Item(1, "test", "testDescription", true, userOwnerId);
        User bookerUser = new User(2, "Test2", "test@email2");
        BookingDto bookingDto = new BookingDto(0
                , LocalDateTime.of(2023, 9, 1, 13, 2, 1)
                , LocalDateTime.of(2024, 1, 1, 13, 2, 1)
                , userBookerId, itemForBooking, "WAITING");

        Booking booking = new Booking();
        booking.setId(0);
        booking.setStart(LocalDateTime.of(2023, 9, 1, 13, 2, 1));
        booking.setEnd(LocalDateTime.of(2024, 1, 1, 13, 2, 1));
        booking.setStatus("WAITING");
        booking.setBooker(bookerUser);
        booking.setItem(expectedItem);

        Mockito.when(userService.getUser(userBookerId)).thenReturn(UserMapper.toUserDto(bookerUser));
        Mockito.when(itemService.getItem(bookingDto.getItemId())).thenReturn(expectedItem);
        Mockito.when(bookingRepository.save(booking)).thenReturn(booking);
        BookingDtoForResponse bookingDtoForResponse = bookingService.addBooking(bookingDto, userBookerId);

        Assertions.assertEquals(bookingDto.getId(), bookingDtoForResponse.getId());
        Assertions.assertEquals(bookingDto.getStart(), bookingDtoForResponse.getStart());
        Assertions.assertEquals(bookingDto.getEnd(), bookingDtoForResponse.getEnd());
        Assertions.assertEquals(bookingDto.getBookerId(), bookingDtoForResponse.getBooker().getId());
        Assertions.assertEquals(bookingDto.getItemId(), bookingDtoForResponse.getItem().getId());
        Assertions.assertEquals(bookingDto.getStatus(), bookingDtoForResponse.getStatus());

        verify(userService).getUser(userBookerId);
        verify(itemService).getItem(bookingDto.getItemId());
        verify(bookingRepository).save(booking);
    }

    @Test
    void addBooking_whenItemNotAvailable_thenItemUnavailableException() {
        int userBookerId = 2;
        int userOwnerId = 1;
        int itemForBooking = 1;
        Item expectedItem = new Item(1, "test", "testDescription", false, userOwnerId);
        User bookerUser = new User(2, "Test2", "test@email2");
        BookingDto bookingDto = new BookingDto(0
                , LocalDateTime.of(2023, 9, 1, 13, 2, 1)
                , LocalDateTime.of(2024, 1, 1, 13, 2, 1)
                , userBookerId, itemForBooking, "WAITING");


        Mockito.when(userService.getUser(userBookerId)).thenReturn(UserMapper.toUserDto(bookerUser));
        Mockito.when(itemService.getItem(bookingDto.getItemId())).thenReturn(expectedItem);

        Assertions.assertThrows(ItemUnavailableException.class, () -> bookingService
                .addBooking(bookingDto, userBookerId));
    }

    @Test
    void addBooking_whenOwnerItemAddBooking_thenBookingNotFoundException() {
        int userBookerId = 2;
        int itemForBooking = 1;
        BookingDto bookingDto = new BookingDto(0
                , LocalDateTime.of(2023, 9, 1, 13, 2, 1)
                , LocalDateTime.of(2024, 1, 1, 13, 2, 1)
                , userBookerId, itemForBooking, "WAITING");
        Item expectedItem = new Item(1, "test", "testDescription", true, userBookerId);
        User bookerUser = new User(2, "Test2", "test@email2");

        Mockito.when(userService.getUser(userBookerId)).thenReturn(UserMapper.toUserDto(bookerUser));
        Mockito.when(itemService.getItem(bookingDto.getItemId())).thenReturn(expectedItem);

        Assertions.assertThrows(BookingNotFoundException.class, () -> bookingService
                .addBooking(bookingDto, userBookerId));
    }

    @Test
    void getBooking_whenBookingValid_thenReturnedBooking() {
        int bookingId = 1;
        int userId = 2;
        Item expectedItem = new Item(1, "test", "testDescription", true, userId);
        User bookerUser = new User(2, "Test2", "test@email2");
        Booking booking = new Booking();
        booking.setId(1);
        booking.setStart(LocalDateTime.of(2023, 9, 1, 13, 2, 1));
        booking.setEnd(LocalDateTime.of(2024, 1, 1, 13, 2, 1));
        booking.setStatus("WAITING");
        booking.setBooker(bookerUser);
        booking.setItem(expectedItem);

        Mockito.when(bookingRepository.findBooking(bookingId, userId)).thenReturn(booking);

        BookingDtoForResponse actualBooking = bookingService.getBooking(userId, bookingId);
        Assertions.assertEquals(booking.getId(), actualBooking.getId());
        Assertions.assertEquals(booking.getStart(), actualBooking.getStart());
        Assertions.assertEquals(booking.getEnd(), actualBooking.getEnd());
        Assertions.assertEquals(booking.getStatus(), actualBooking.getStatus());
    }

    @Test
    void getBooking_whenBookingNotFound_thenReturnedBookingNotFoundException() {
        int bookingId = 1;
        int userId = 2;

        Mockito.when(bookingRepository.findBooking(bookingId, userId)).thenReturn(null);

        Assertions.assertThrows(BookingNotFoundException.class, () -> bookingService.getBooking(userId, bookingId));
    }

    @Test
    void approvedBooking_whenBookingFoundAndStatusTrue_thenReturnedBooking() {
        int bookingId = 1;
        int userId = 1;
        Item expectedItem = new Item(1, "test", "testDescription", true, userId);
        User bookerUser = new User(2, "Test2", "test@email2");
        Booking booking = new Booking();
        booking.setId(1);
        booking.setStart(LocalDateTime.of(2023, 9, 1, 13, 2, 1));
        booking.setEnd(LocalDateTime.of(2024, 1, 1, 13, 2, 1));
        booking.setStatus("WAITING");
        booking.setBooker(bookerUser);
        booking.setItem(expectedItem);

        Mockito.when(bookingRepository.findBookingForApprove(bookingId, userId)).thenReturn(booking);
        Mockito.when(bookingRepository.save(booking)).thenReturn(booking);

        BookingDtoForResponse actualBooking = bookingService.approvedBooking(true, userId, bookingId);

        Assertions.assertEquals(booking.getId(), actualBooking.getId());
        Assertions.assertEquals(booking.getStart(), actualBooking.getStart());
        Assertions.assertEquals(booking.getEnd(), actualBooking.getEnd());
        Assertions.assertEquals("APPROVED", actualBooking.getStatus());
    }

    @Test
    void approvedBooking_whenBookingFoundAndStatusFalse_thenReturnedBooking() {
        int bookingId = 1;
        int userId = 1;
        Item expectedItem = new Item(1, "test", "testDescription", true, userId);
        User bookerUser = new User(2, "Test2", "test@email2");
        Booking booking = new Booking();
        booking.setId(1);
        booking.setStart(LocalDateTime.of(2023, 9, 1, 13, 2, 1));
        booking.setEnd(LocalDateTime.of(2024, 1, 1, 13, 2, 1));
        booking.setStatus("WAITING");
        booking.setBooker(bookerUser);
        booking.setItem(expectedItem);


        Mockito.when(bookingRepository.findBookingForApprove(bookingId, userId)).thenReturn(booking);
        Mockito.when(bookingRepository.save(booking)).thenReturn(booking);

        BookingDtoForResponse actualBooking = bookingService.approvedBooking(false, userId, bookingId);

        Assertions.assertEquals(booking.getId(), actualBooking.getId());
        Assertions.assertEquals(booking.getStart(), actualBooking.getStart());
        Assertions.assertEquals(booking.getEnd(), actualBooking.getEnd());
        Assertions.assertEquals("REJECTED", actualBooking.getStatus());
    }

    @Test
    void approvedBooking_whenBookingAlreadyApproved_thenReturnedValidationException() {
        int bookingId = 1;
        int userId = 1;
        Item expectedItem = new Item(1, "test", "testDescription", true, userId);
        User bookerUser = new User(2, "Test2", "test@email2");
        Booking booking = new Booking();
        booking.setId(1);
        booking.setStart(LocalDateTime.of(2023, 9, 1, 13, 2, 1));
        booking.setEnd(LocalDateTime.of(2024, 1, 1, 13, 2, 1));
        booking.setStatus("APPROVED");
        booking.setBooker(bookerUser);
        booking.setItem(expectedItem);

        Mockito.when(bookingRepository.findBookingForApprove(bookingId, userId)).thenReturn(booking);

        Assertions.assertThrows(ValidationException.class, () -> bookingService
                .approvedBooking(true, bookingId, userId));
    }

    @Test
    void approvedBooking_whenBookingNotFound_thenReturnedBookingNotFoundException() {
        int bookingId = 1;
        int userId = 1;

        Mockito.when(bookingRepository.findBookingForApprove(bookingId, userId)).thenReturn(null);

        Assertions.assertThrows(BookingNotFoundException.class, () -> bookingService
                .approvedBooking(true, bookingId, userId));
    }

    @Test
    void getBookingWithState_whenStateIsAll_thenReturnedBookingWithAllState() {
        int userId = 3;
        int from = 1;
        int size = 1;
        Item expectedItem = new Item(1, "test", "testDescription", true, 1);
        User bookerUser = new User(2, "Test2", "test@email2");
        Booking booking = new Booking();
        booking.setId(1);
        booking.setStart(LocalDateTime.of(2023, 9, 1, 13, 2, 1));
        booking.setEnd(LocalDateTime.of(2024, 1, 1, 13, 2, 1));
        booking.setStatus("APPROVED");
        booking.setBooker(bookerUser);
        booking.setItem(expectedItem);
        List<Booking> bookingExpectedList = new ArrayList<>();
        bookingExpectedList.add(booking);


        Mockito.when(bookingRepository.findByBookerIdOrderByStartDesc(userId, from, size))
                .thenReturn(bookingExpectedList);

        List<BookingDtoForResponse> bookingActualList = bookingService
                .getBookingWithState(userId, "ALL", from, size);


        Assertions.assertEquals(bookingExpectedList.size(), bookingActualList.size());
        Assertions.assertEquals(bookingExpectedList.get(0).getId(), bookingActualList.get(0).getId());
        Assertions.assertEquals(bookingExpectedList.get(0).getStart(), bookingActualList.get(0).getStart());
        Assertions.assertEquals(bookingExpectedList.get(0).getEnd(), bookingActualList.get(0).getEnd());
        Assertions.assertEquals(bookingExpectedList.get(0).getStatus(), bookingActualList.get(0).getStatus());
    }


    @Test
    void getBookingWithState_whenStateIsCurrent_thenReturnedBookingWithCurrentState() {
        int userId = 3;
        int from = 1;
        int size = 1;
        Item expectedItem = new Item(1, "test", "testDescription", true, 1);
        User bookerUser = new User(2, "Test2", "test@email2");
        Booking booking = new Booking();
        booking.setId(1);
        booking.setStart(LocalDateTime.of(2023, 9, 1, 13, 2, 1));
        booking.setEnd(LocalDateTime.of(2024, 1, 1, 13, 2, 1));
        booking.setStatus("CURRENT");
        booking.setBooker(bookerUser);
        booking.setItem(expectedItem);
        List<Booking> bookingExpectedList = new ArrayList<>();
        bookingExpectedList.add(booking);


        Mockito.when(bookingRepository.findBookingWithCurrentStatus(userId, from, size))
                .thenReturn(bookingExpectedList);

        List<BookingDtoForResponse> bookingActualList = bookingService
                .getBookingWithState(userId, "CURRENT", from, size);

        Assertions.assertEquals(bookingExpectedList.size(), bookingActualList.size());
        Assertions.assertEquals(bookingExpectedList.get(0).getId(), bookingActualList.get(0).getId());
        Assertions.assertEquals(bookingExpectedList.get(0).getStart(), bookingActualList.get(0).getStart());
        Assertions.assertEquals(bookingExpectedList.get(0).getEnd(), bookingActualList.get(0).getEnd());
        Assertions.assertEquals(bookingExpectedList.get(0).getStatus(), bookingActualList.get(0).getStatus());
    }

    @Test
    void getBookingWithState_whenStateIsFuture_thenReturnedBookingWithFutureState() {
        int userId = 3;
        int from = 1;
        int size = 1;
        Item expectedItem = new Item(1, "test", "testDescription", true, 1);
        User bookerUser = new User(2, "Test2", "test@email2");
        Booking booking = new Booking();
        booking.setId(1);
        booking.setStart(LocalDateTime.of(2023, 9, 1, 13, 2, 1));
        booking.setEnd(LocalDateTime.of(2024, 1, 1, 13, 2, 1));
        booking.setStatus("FUTURE");
        booking.setBooker(bookerUser);
        booking.setItem(expectedItem);
        List<Booking> bookingExpectedList = new ArrayList<>();
        bookingExpectedList.add(booking);

        Mockito.when(bookingRepository.findBookingFuture(userId, from, size))
                .thenReturn(bookingExpectedList);

        List<BookingDtoForResponse> bookingActualList = bookingService
                .getBookingWithState(userId, "FUTURE", from, size);

        Assertions.assertEquals(bookingExpectedList.size(), bookingActualList.size());
        Assertions.assertEquals(bookingExpectedList.get(0).getId(), bookingActualList.get(0).getId());
        Assertions.assertEquals(bookingExpectedList.get(0).getStart(), bookingActualList.get(0).getStart());
        Assertions.assertEquals(bookingExpectedList.get(0).getEnd(), bookingActualList.get(0).getEnd());
        Assertions.assertEquals(bookingExpectedList.get(0).getStatus(), bookingActualList.get(0).getStatus());
    }

    @Test
    void getBookingWithState_whenStateIsWaiting_thenReturnedBookingWithWaitingState() {
        int userId = 3;
        int from = 1;
        int size = 1;
        Item expectedItem = new Item(1, "test", "testDescription", true, 1);
        User bookerUser = new User(2, "Test2", "test@email2");
        Booking booking = new Booking();
        booking.setId(1);
        booking.setStart(LocalDateTime.of(2023, 9, 1, 13, 2, 1));
        booking.setEnd(LocalDateTime.of(2024, 1, 1, 13, 2, 1));
        booking.setStatus("WAITING");
        booking.setBooker(bookerUser);
        booking.setItem(expectedItem);
        List<Booking> bookingExpectedList = new ArrayList<>();
        bookingExpectedList.add(booking);


        Mockito.when(bookingRepository.findBookingWithWaitingStatus(userId, from, size))
                .thenReturn(bookingExpectedList);

        List<BookingDtoForResponse> bookingActualList = bookingService
                .getBookingWithState(userId, "WAITING", from, size);

        Assertions.assertEquals(bookingExpectedList.size(), bookingActualList.size());
        Assertions.assertEquals(bookingExpectedList.get(0).getId(), bookingActualList.get(0).getId());
        Assertions.assertEquals(bookingExpectedList.get(0).getStart(), bookingActualList.get(0).getStart());
        Assertions.assertEquals(bookingExpectedList.get(0).getEnd(), bookingActualList.get(0).getEnd());
        Assertions.assertEquals(bookingExpectedList.get(0).getStatus(), bookingActualList.get(0).getStatus());
    }

    @Test
    void getBookingWithState_whenStateIsRejected_thenReturnedBookingWithRejectedState() {
        int userId = 3;
        int from = 1;
        int size = 1;
        Item expectedItem = new Item(1, "test", "testDescription", true, 1);
        User bookerUser = new User(2, "Test2", "test@email2");
        Booking booking = new Booking();
        booking.setId(1);
        booking.setStart(LocalDateTime.of(2023, 9, 1, 13, 2, 1));
        booking.setEnd(LocalDateTime.of(2024, 1, 1, 13, 2, 1));
        booking.setStatus("REJECTED");
        booking.setBooker(bookerUser);
        booking.setItem(expectedItem);
        List<Booking> bookingExpectedList = new ArrayList<>();
        bookingExpectedList.add(booking);

        Mockito.when(bookingRepository.findBookingWithRejectedStatus(userId, from, size))
                .thenReturn(bookingExpectedList);

        List<BookingDtoForResponse> bookingActualList = bookingService
                .getBookingWithState(userId, "REJECTED", from, size);

        Assertions.assertEquals(bookingExpectedList.size(), bookingActualList.size());
        Assertions.assertEquals(bookingExpectedList.get(0).getId(), bookingActualList.get(0).getId());
        Assertions.assertEquals(bookingExpectedList.get(0).getStart(), bookingActualList.get(0).getStart());
        Assertions.assertEquals(bookingExpectedList.get(0).getEnd(), bookingActualList.get(0).getEnd());
        Assertions.assertEquals(bookingExpectedList.get(0).getStatus(), bookingActualList.get(0).getStatus());
    }

    @Test
    void getBookingWithState_whenStateIsPast_thenReturnedBookingWithPastState() {
        int userId = 3;
        int from = 1;
        int size = 1;
        Item expectedItem = new Item(1, "test", "testDescription", true, 1);
        User bookerUser = new User(2, "Test2", "test@email2");
        Booking booking = new Booking();
        booking.setId(1);
        booking.setStart(LocalDateTime.of(2023, 9, 1, 13, 2, 1));
        booking.setEnd(LocalDateTime.of(2024, 1, 1, 13, 2, 1));
        booking.setStatus("PAST");
        booking.setBooker(bookerUser);
        booking.setItem(expectedItem);
        List<Booking> bookingExpectedList = new ArrayList<>();
        bookingExpectedList.add(booking);

        Mockito.when(bookingRepository.findBookingInPast(userId, from, size))
                .thenReturn(bookingExpectedList);

        List<BookingDtoForResponse> bookingActualList = bookingService
                .getBookingWithState(userId, "PAST", from, size);

        Assertions.assertEquals(bookingExpectedList.size(), bookingActualList.size());
        Assertions.assertEquals(bookingExpectedList.get(0).getId(), bookingActualList.get(0).getId());
        Assertions.assertEquals(bookingExpectedList.get(0).getStart(), bookingActualList.get(0).getStart());
        Assertions.assertEquals(bookingExpectedList.get(0).getEnd(), bookingActualList.get(0).getEnd());
        Assertions.assertEquals(bookingExpectedList.get(0).getStatus(), bookingActualList.get(0).getStatus());
    }


}
