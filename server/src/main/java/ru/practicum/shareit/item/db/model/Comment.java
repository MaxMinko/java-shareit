package ru.practicum.shareit.item.db.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.practicum.shareit.user.db.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comments")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String text;
    @ManyToOne
    @JoinColumn(name = "item_id")
    Item item;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User author;
    LocalDateTime created;

    public Comment(int id, String text, Item item, User user, LocalDateTime created) {
        this.id = id;
        this.text = text;
        this.item = item;
        this.author = user;
        this.created = created;
    }

    public Comment() {

    }
}
