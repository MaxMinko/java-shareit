package ru.practicum.shareit.item.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.web.dto.CommentDto;
import ru.practicum.shareit.item.web.dto.ItemDto;

import java.util.Map;
@Service
public class ItemClient extends BaseClient {

    private static final String API_PREFIX = "/items";

    private final Logger logger = LoggerFactory.getLogger(getClass());


    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(  builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }



    public ResponseEntity<Object> addItem(int userId,ItemDto itemDto){
        logger.info("save user dto {}", itemDto);
        return post("",userId,itemDto);
    }

    public ResponseEntity<Object> updateItem( int userId,
                               int itemId,
                              ItemDto itemDto) {
        logger.info("update item by id = {}", itemId);
        return patch("/"+itemId, userId, itemDto);
    }

    public ResponseEntity<Object> getAllUserItems(int userId) {
        logger.info("get all user items");
        return get("",userId);
    }

    public ResponseEntity<Object> getItem( int userId, int itemId) {
        logger.info("get item by id = {}", itemId);
        return get("/"+itemId,userId);
    }

    public ResponseEntity<Object> getItemByDescription(String text) {
        logger.info("get items by text = {}", text);
        Map<String,Object> parameters = Map.of( "text", text);
        return get("/search?text={text}",null,parameters);
    }


    public ResponseEntity<Object> addComment( int userId,
                                int itemId, CommentDto commentDto) {
        logger.info("save comment dto {}", commentDto);
        return post("/"+itemId+"/comment",userId,commentDto);
    }


}
