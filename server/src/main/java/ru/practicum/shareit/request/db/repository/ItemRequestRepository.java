package ru.practicum.shareit.request.db.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.request.db.model.ItemRequest;

import java.util.List;

public interface ItemRequestRepository extends JpaRepository<ItemRequest, Integer> {
    List<ItemRequest> findByUserId(int userId);

    Page<ItemRequest> findByUserIdIsNot(int requestId, PageRequest pageRequest);
}
