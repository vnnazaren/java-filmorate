package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryUserStorage implements UserStorage {
    protected final List<User> list = new ArrayList<>();

    public User createUser(User user) {
        list.add(user);
        return user;
    }

    public User updateUser(User user) {
        User userToUpdate = getUserById(user.getId());
        list.set(list.indexOf(userToUpdate), user);
        return user;
    }

    public List<User> getUsers() {
        return list;
    }

    public User getUserById(int id) {
        return list.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(String.format("Пользователь c id - %s не найден", id)));
    }
}
