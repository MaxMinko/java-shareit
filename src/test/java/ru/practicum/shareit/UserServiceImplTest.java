package ru.practicum.shareit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.UserServiceImpl;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.validator.UserValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserValidator userValidator;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl service;


    @Test
    void getUser_whenUserFound_thenReturnedUser() {
        UserDto expectedUser = new UserDto(1, "Test", "test@email");

        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(UserMapper.toUser(expectedUser)));
        UserDto actualUser = service.getUser(1);

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    void getUser_whenUserNotFound_thenReturnedUserNotFoundException() {
        int userId = 0;

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> service.getUser(userId));
    }

    @Test
    void addUser_whenUserValid_thenReturnedSavedUser() {
        UserDto savedUser = new UserDto(1, "Test", "test@email");

        Mockito.when(userRepository.save(UserMapper.toUser(savedUser))).thenReturn(UserMapper.toUser(savedUser));
        UserDto actualUser = service.addUser(savedUser);

        Assertions.assertEquals(savedUser, actualUser);
        verify(userRepository).save(UserMapper.toUser(savedUser));
    }


    @Test
    void addUser_whenUserNotValid_thenReturnedValidationException() {
        UserDto savedUser = new UserDto(1, "Test", "test@email");

        doThrow(ValidationException.class)
                .when(userValidator).checkUserDtoForCreate(savedUser);

        Assertions.assertThrows(ValidationException.class, () -> userValidator.checkUserDtoForCreate(savedUser));
        verify(userRepository, never()).save(UserMapper.toUser(savedUser));
    }

    @Test
    void updateUser_whenUserForUpdateValid_thenReturnedSavedUser() {
        int userIdForUpdate = 1;
        UserDto newUser = new UserDto(1, "updateName", "updateTest@email");

        Mockito.when(userRepository.updateUser(newUser, userIdForUpdate)).thenReturn(UserMapper.toUser(newUser));

        UserDto actualUser = service.updateUser(newUser, newUser.getId());
        verify(userRepository).updateUser(newUser, newUser.getId());
        Assertions.assertEquals(newUser.getId(), actualUser.getId());
        Assertions.assertEquals(newUser.getName(), actualUser.getName());
        Assertions.assertEquals(newUser.getEmail(), actualUser.getEmail());
    }

    @Test
    void updateUser_whenUserForUpdateNotValid_thenReturnedValidationException() {
        int userIdForUpdate = 1;
        UserDto newUser = new UserDto(1, "updateName", "updateTest@email");

        doThrow(ValidationException.class)
                .when(userValidator).checkUserDtoForUpdate(newUser, userIdForUpdate);

        Assertions.assertThrows(ValidationException.class,
                () -> userValidator.checkUserDtoForUpdate(newUser, userIdForUpdate));
        verify(userRepository, never()).updateUser(newUser, userIdForUpdate);
    }

    @Test
    void getAllUser_whenUserFound_thenReturnedAllUser() {
        User expectedUser = new User(1, "Test", "test@email");
        List<User> expectedUserList = new ArrayList<>();
        expectedUserList.add(expectedUser);

        Mockito.when(userRepository.findAll()).thenReturn(expectedUserList);
        List<UserDto> actualUsersList = service.getAllUsers();

        verify(userRepository).findAll();
        Assertions.assertEquals(expectedUserList.size(), actualUsersList.size());
        Assertions.assertEquals(expectedUserList.get(0).getId(), actualUsersList.get(0).getId());
        Assertions.assertEquals(expectedUserList.get(0).getName(), actualUsersList.get(0).getName());
        Assertions.assertEquals(expectedUserList.get(0).getEmail(), actualUsersList.get(0).getEmail());
    }


}
