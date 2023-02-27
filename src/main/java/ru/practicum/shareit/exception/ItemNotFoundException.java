package ru.practicum.shareit.exception;

import ru.practicum.shareit.item.ItemMapper;

public class ItemNotFoundException extends RuntimeException{
    public ItemNotFoundException(String message){
        super(message);
    }
}
