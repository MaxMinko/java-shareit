package ru.practicum.shareit.item;


import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

public class CommentMapper {
    public static Comment toComment(CommentDto commentDto, User user, Item item){
        return new Comment(commentDto.getId(), commentDto.getText(),item,user,commentDto.getCreated());
    }
    public static CommentDto toCommentDto(Comment comment){
        return new CommentDto(comment.getId(),comment.getText(),comment.getAuthor().getName(),comment.getItem().getId()
                ,comment.getCreated());
    }
}

