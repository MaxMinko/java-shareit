package ru.practicum.shareit.booking.db.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.db.model.Booking;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    @Query(value = "select * " +
            "FROM bookers inner join items i on i.id = bookers.item_id "
            + "where bookers.id=?1 "
            + "AND i.user_id=?2 ", nativeQuery = true)
    Booking findBookingForApprove(int bookingId, int userId);

    @Query(value = "select * " +
            "FROM bookers inner join items i on i.id = bookers.item_id " +
            "where bookers.id=?1 " +
            "AND bookers.booker_id=?2 " +
            "OR bookers.id=?1 AND i.user_id=?2 ", nativeQuery = true)
    Booking findBooking(int bookingId, int userId);

    @Query(value = " select *  " +
            "from bookers " +
            "where booker_id=?1 " +
            "order by start_time DESC " +
            "LIMIT ?3 OFFSET ?2", nativeQuery = true)
    List<Booking> findByBookerIdOrderByStartDesc(int userId, int from, int size);

    @Query(value = "select * " +
            "from bookers " +
            "where booker_id=?1 " +
            "AND start_time >=now() " +
            "ORDER BY start_time DESC LIMIT ?3 OFFSET ?2 ", nativeQuery = true)
    List<Booking> findBookingFuture(int userId, int from, int size);

    @Query(value = "select * " +
            "from bookers " +
            "where booker_id=?1 " +
            "AND status LIKE 'WAITING' LIMIT ?3 OFFSET ?2 ", nativeQuery = true)
    List<Booking> findBookingWithWaitingStatus(int userId, int from, int size);

    @Query(value = "select *" +
            "from bookers " +
            "where booker_id=?1 AND " +
            "now() between start_time and end_time LIMIT ?3 OFFSET ?2 ", nativeQuery = true)
    List<Booking> findBookingWithCurrentStatus(int userId, int from, int size);

    @Query(value = "select * " +
            "from bookers " +
            "where booker_id=?1 AND " +
            "  end_time<=now()    order by start_time DESC LIMIT ?3 OFFSET ?2 ", nativeQuery = true)
    List<Booking> findBookingInPast(int userId, int from, int size);

    @Query(value = "select * " +
            "from bookers " +
            "where booker_id=?1 " +
            "AND status LIKE 'REJECTED' LIMIT ?3 OFFSET ?2 ", nativeQuery = true)
    List<Booking> findBookingWithRejectedStatus(int userId, int from, int size);

    @Query(value = " select *  " +
            "from bookers inner join items i on i.id = bookers.item_id " +
            "where i.user_id=?1 " +
            "order by start_time DESC " +
            "LIMIT ?3 OFFSET ?2", nativeQuery = true)
    List<Booking> findByItem_userIdOrderByStartDesc(int userId, int from, int size);

    @Query(value = "select * " +
            "FROM bookers inner join items i on i.id = bookers.item_id " +
            "where i.user_id=?1 " +
            "AND bookers.start_time>=now() " +
            "order by bookers.start_time DESC LIMIT ?3 OFFSET ?2 ", nativeQuery = true)
    List<Booking> findBookingFutureByOwner(int userId, int from, int size);

    @Query(value = "select * " +
            "FROM bookers " +
            "where item_id=?1 and status  ILIKE 'APPROVED' AND start_time>now() " +
            "order by start_time LIMIT 1", nativeQuery = true)
    Booking findNextBookingForItem(int itemId);

    @Query(value = "select * " +
            "FROM bookers " +
            "where item_id=?1 and status  ILIKE 'APPROVED' AND start_time<=now() " +
            "order  by start_time DESC LIMIT 1", nativeQuery = true)
    Booking findLastBookingForItem(int itemId);

    @Query(value = "select * " +
            "FROM bookers inner join items i on i.id = bookers.item_id " +
            "where i.user_id=?1 " +
            "AND bookers.status LIKE 'WAITING' LIMIT ?3 OFFSET ?2", nativeQuery = true)
    List<Booking> findWaitingBookingByOwner(int userId, int from, int size);

    @Query(value = "select * " +
            "FROM bookers inner join items i on i.id = bookers.item_id " +
            "where i.user_id=?1 AND " +
            "now() BETWEEN start_time AND end_time LIMIT ?3 OFFSET ?2 ", nativeQuery = true)
    List<Booking> findCurrentBookingByOwner(int userId, int from, int size);

    @Query(value = "select * " +
            "FROM bookers inner join items i on i.id = bookers.item_id " +
            "where i.user_id=?1 AND " +
            "  end_time<=now()   order by start_time DESC LIMIT ?3 OFFSET ?2", nativeQuery = true)
    List<Booking> findBookingInPastByOwner(int userId, int from, int size);

    @Query(value = "select * " +
            "FROM bookers inner join items i on i.id = bookers.item_id " +
            "where i.user_id=?1 " +
            "AND bookers.status LIKE 'REJECTED' LIMIT ?3 OFFSET ?2 ", nativeQuery = true)
    List<Booking> findRejectedBookingByOwner(int userId, int from, int size);

    @Query(value = "select COUNT(id) " +
            "FROM bookers " +
            "where booker_id=?1 and item_id=?2 " +
            "AND end_time<now()", nativeQuery = true)
    Integer findBookingForComments(int userId, int itemId);
}
