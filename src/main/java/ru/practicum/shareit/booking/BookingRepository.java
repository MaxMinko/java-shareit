package ru.practicum.shareit.booking;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;


import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query(value = "select * " +
            "FROM bookers inner join items i on i.id = bookers.item_id " +
            "where bookers.id=?1 " +
            "AND i.user_id=?2 "
            , nativeQuery = true)
    Booking findBookingForApprove(int bookingId, int userId);

    @Query(value = "select * " +
            "FROM bookers inner join items i on i.id = bookers.item_id " +
            "where bookers.id=?1 " +
            "AND bookers.booker_id=?2 " +
            "OR bookers.id=?1 AND i.user_id=?2 "
            , nativeQuery = true)
    Booking findBooking(int bookingId, int userId);

    List<Booking> findByBookerIdOrderByStartDesc(int userId);

    @Query(value = "select * " +
            "from bookers " +
            "where booker_id=? " +
            "AND start_time >=now() " +
            "ORDER BY start_time DESC "
            , nativeQuery = true)
    List<Booking> findBookingFuture(int userId);

    @Query(value = "select * " +
            "from bookers " +
            "where booker_id=? " +
            "AND status LIKE 'WAITING' "
            , nativeQuery = true)
    List<Booking> findBookingWithWaitingStatus(int userId);

    @Query(value = "select *" +
            "from bookers " +
            "where booker_id=? AND " +
            "now() between start_time and end_time"
            , nativeQuery = true)
    List<Booking> findBookingWithCurrentStatus(int userId);


    @Query(value = "select * " +
            "from bookers " +
            "where booker_id=? AND " +
            "  end_time<=now()    order by start_time DESC"
            , nativeQuery = true)
    List<Booking> findBookingInPast(int userId);


    @Query(value = "select * " +
            "from bookers " +
            "where booker_id=? " +
            "AND status LIKE 'REJECTED' "
            , nativeQuery = true)
    List<Booking> findBookingWithRejectedStatus(int userId);

    List<Booking> findByItem_userIdOrderByStartDesc(int userId);

    @Query(value = "select * " +
            "FROM bookers inner join items i on i.id = bookers.item_id " +
            "where i.user_id=? " +
            "AND bookers.start_time>=now() " +
            "order by bookers.start_time DESC "
            , nativeQuery = true)
    List<Booking> findBookingFutureByOwner(int userId);

    @Query(value = "select * " +
            "FROM bookers " +
            "where item_id=?1 and status  ILIKE 'APPROVED' AND start_time>now() " +
            "order by start_time LIMIT 1"
            , nativeQuery = true)
    Booking findNextBookingForItem(int itemId);

    @Query(value = "select * " +
            "FROM bookers " +
            "where item_id=?1 and status  ILIKE 'APPROVED' AND start_time<=now() " +
            "order  by start_time DESC LIMIT 1"
            , nativeQuery = true)
    Booking findLastBookingForItem(int itemId);

    @Query(value = "select * " +
            "FROM bookers inner join items i on i.id = bookers.item_id " +
            "where i.user_id=? " +
            "AND bookers.status LIKE 'WAITING' "
            , nativeQuery = true)
    List<Booking> findWaitingBookingByOwner(int userId);

    @Query(value = "select * " +
            "FROM bookers inner join items i on i.id = bookers.item_id " +
            "where i.user_id=? AND " +
            "now() BETWEEN start_time AND end_time"
            , nativeQuery = true)
    List<Booking> findCurrentBookingByOwner(int userId);

    @Query(value = "select * " +
            "FROM bookers inner join items i on i.id = bookers.item_id " +
            "where i.user_id=? AND " +
            "  end_time<=now()   order by start_time DESC"
            , nativeQuery = true)
    List<Booking> findBookingInPastByOwner(int userId);

    @Query(value = "select * " +
            "FROM bookers inner join items i on i.id = bookers.item_id " +
            "where i.user_id=? " +
            "AND bookers.status LIKE 'REJECTED' "
            , nativeQuery = true)
    List<Booking> findRejectedBookingByOwner(int userId);

    @Query(value = "select COUNT(id) " +
            "FROM bookers " +
            "where booker_id=?1 and item_id=?2 " +
            "AND end_time<now()"
            , nativeQuery = true)
    Integer findBookingForComments(int userId, int itemId);
}
