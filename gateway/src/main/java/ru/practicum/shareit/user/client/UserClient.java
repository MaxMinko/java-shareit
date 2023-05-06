package ru.practicum.shareit.user.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.user.web.dto.UserDto;
import ru.practicum.shareit.validator.UserValidator;

@Service
public class UserClient extends BaseClient {
    private final UserValidator userValidator=new UserValidator();
    private static final String API_PREFIX = "/users";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public UserClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(  builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }

    public ResponseEntity<Object> addUser(UserDto userDto){
        userValidator.checkUserDtoForCreate(userDto);
        logger.info("save user dto {}", userDto);
        return post("",userDto);
    }

    public ResponseEntity<Object> updateUser(int userId, UserDto userDto) {
        userValidator.checkUserDtoForUpdate(userDto, userId);
        logger.info("update user by id = {}", userId);
        return patch("/"+userId,userDto);
    }

    public ResponseEntity<Object> getUser(int userId) {
        logger.info("get user by id = {}", userId);
        return get("/"+userId);
    }

    public void deleteUser(int userId) {
        logger.info("delete user by id = {}", userId);
        delete("/"+userId);
    }

    public ResponseEntity<Object> getAllUsers() {
        logger.info("get all users");
        return get("");
    }
}
