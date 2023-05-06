package ru.practicum.shareit.request.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.request.web.dto.ItemRequestDto;

import java.util.Map;

@Service
public class ItemRequestClient extends BaseClient {
    private static final String API_PREFIX = "/requests";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public ItemRequestClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }

    public ResponseEntity<Object> addRequest(int userId, ItemRequestDto itemRequestDto) {
        logger.info("save itemRequest dto {}", itemRequestDto);
        return post("", userId, itemRequestDto);
    }


    public ResponseEntity<Object> getAllUserItemRequest(int userId) {
        logger.info("get allUserItemRequest with id {}", userId);
        return get("", userId);
    }


    public ResponseEntity<Object> getItemRequest(int userId, int requestId) {
        logger.info("get itemRequests with requestId {}", requestId);
        return get("/" + requestId, userId);
    }


    public ResponseEntity<Object> getListWithOtherUserRequest(int userId, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size
        );
        return get("/all?from={from}&size={size}", Long.valueOf(userId), parameters);

    }


}
