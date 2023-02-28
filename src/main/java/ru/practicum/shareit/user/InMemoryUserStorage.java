package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.user.model.User;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {
    private Map<Integer, User> users = new HashMap();
    private int userId = 1;

    @Override
    public User addUser(User user) {
        user.setId(userId++);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User updateUser(User user, int userId) {
        if (users.containsKey(userId)) {
            if (user.getEmail() != null) {
                getUser(userId).setEmail(user.getEmail());
            }
            if (user.getName() != null) {
                getUser(userId).setName(user.getName());
            }
        }
        return getUser(userId);
    }

    @Override
    public User getUser(int userId) {
        return users.get(userId);
    }

    @Override
    public void userDelete(int userId) {
        users.remove(userId);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        for (User user : users.values()) {
            allUsers.add(user);
        }
        return allUsers;
    }
}
