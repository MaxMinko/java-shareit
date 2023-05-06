package ru.practicum.shareit.booking.db.model;


import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.db.model.Item;
import ru.practicum.shareit.user.db.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bookers")
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "start_time")
    LocalDateTime start;
    @Column(name = "end_time")
    LocalDateTime end;

    @ManyToOne()
    User booker;
    @ManyToOne()
    Item item;
    String status;

    public Booking() {

    }

    public Booking(int id, LocalDateTime start, LocalDateTime end, User booker, Item item) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.booker = booker;
        this.item = item;
    }

    public Booking(int id, User booker, Item item) {
        this.id = id;
        this.booker = booker;
        this.item = item;
    }

}
