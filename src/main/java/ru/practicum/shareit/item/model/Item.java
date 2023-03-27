package ru.practicum.shareit.item.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;


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
    @Column(name ="request_id")
    int requestId;


    public Item(int id, String name, String description, boolean available,int userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.userId =userId;
    }
    public Item(int id, String name, String description, boolean available,int userId,int requestId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
        this.userId =userId;
        this.requestId=requestId;
    }



    public Item() {

    }
}
