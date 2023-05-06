package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDto {
    int id;
    @NotEmpty()
    String text;
    String authorName;
    int itemId;
    LocalDateTime created;

    public CommentDto() {

    }

    public CommentDto(int id, String text, String authorName,
                      int itemId,
                      LocalDateTime created) {
        this.id = id;
        this.text = text;
        this.authorName = authorName;
        this.itemId = itemId;
        this.created = created;
    }

    public CommentDto(int id, String text, String authorName,
                      int itemId) {
        this.id = id;
        this.text = text;
        this.authorName = authorName;
        this.itemId = itemId;
    }

}

