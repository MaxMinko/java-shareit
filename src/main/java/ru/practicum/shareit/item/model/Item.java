package ru.practicum.shareit.item.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.lang.NonNull;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name="items")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String name;

    String description;

    Boolean available;
    @Column(name ="user_id",nullable = false)
     int userId;
   // mappedBy = "item", fetch=FetchType.EAGER

   // String request;

    public Item(int id, String name, String description, boolean available,int userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.userId =userId;
    }


    public Item() {

    }
}
