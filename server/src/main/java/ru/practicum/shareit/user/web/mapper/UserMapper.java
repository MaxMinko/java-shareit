package ru.practicum.shareit.user.web.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.web.dto.UserDto;
import ru.practicum.shareit.user.db.model.User;
@Component
public class UserMapper {
    public static UserDto toUserDto(User user){
        return new UserDto(user.getId(),user.getName(),user.getEmail());
    }
    public static User toUser(UserDto userDto){
        return new User(userDto.getId(),userDto.getName(),userDto.getEmail());
    }
}