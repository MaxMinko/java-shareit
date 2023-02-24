package ru.practicum.shareit.item.model;

import lombok.*;
import ru.practicum.shareit.user.model.User;


@Getter
@Setter
@EqualsAndHashCode
public class Item {
    int id;
    String name;
    String description;
    Boolean available;
    User owner;
    String request;

    public Item(int id, String name, String description, Boolean available) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.available = available;
    }
}
