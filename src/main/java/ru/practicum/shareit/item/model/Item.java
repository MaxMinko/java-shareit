package ru.practicum.shareit.item.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.user.model.User;


@Data
@AllArgsConstructor
public class Item {
    int id;
    String name;
    String description;
    Boolean available;
    User owner;
    String request;

    public Item(int id, String name, String description, Boolean available) {
    }
}
