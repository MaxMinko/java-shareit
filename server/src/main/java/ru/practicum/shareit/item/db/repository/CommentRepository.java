package ru.practicum.shareit.item.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.db.model.Comment;


import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByItemId(int id);
}
