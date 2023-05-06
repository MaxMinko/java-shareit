package ru.practicum.shareit.item.web.mapper;


import ru.practicum.shareit.item.web.dto.CommentDto;
import ru.practicum.shareit.item.db.model.Comment;
import ru.practicum.shareit.item.db.model.Item;
import ru.practicum.shareit.user.db.model.User;

public class CommentMapper {
    public static Comment toComment(CommentDto commentDto, User user, Item item){
        return new Comment(commentDto.getId(), commentDto.getText(),item,user,commentDto.getCreated());
    }
    public static CommentDto toCommentDto(Comment comment){
        return new CommentDto(comment.getId(),comment.getText(),comment.getAuthor().getName(),comment.getItem().getId()
                ,comment.getCreated());
    }
}

