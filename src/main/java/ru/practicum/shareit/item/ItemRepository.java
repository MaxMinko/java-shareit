package ru.practicum.shareit.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.item.model.Item;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Integer>, ItemRepositoryCustom {
    List<Item> findItemsByUserIdOrderByIdAsc(int id);

    Item findByIdAndUserId(int id, int userId);

    @Query(value = "select * " +
            "from items   " +
            "where (name ILIKE %?1% or description ILIKE %?1%) AND available  is  true"
            , nativeQuery = true)
    List<Item> findByNameAndDescriptionContaining(String text);

    @Query(value = "select * " +
            "from items   " +
            "where request_id=? "
            , nativeQuery = true)
    List<Item> getItemWithRequest(int requestId);
}
