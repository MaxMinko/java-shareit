package ru.practicum.shareit.request.db.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.item.db.model.Item;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "requests")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    int id;
    String description;
    LocalDateTime created;
    @Column(name = "user_id")
    int userId;

    @OneToMany
    @JoinColumn(name = "request_id")
    private List<Item> items;

    public ItemRequest() {
    }
}
