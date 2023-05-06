package ru.practicum.shareit;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    void updateUser() {
        UserDto updateUser = new UserDto(1, "update", "update");
        userRepository.save(new User(1, "test", "test@email"));

        User actualUser = userRepository.updateUser(updateUser, 1);

        Assertions.assertEquals(actualUser.getId(), updateUser.getId());
        Assertions.assertEquals(actualUser.getName(), updateUser.getName());
        Assertions.assertEquals(actualUser.getEmail(), updateUser.getEmail());
    }
}
