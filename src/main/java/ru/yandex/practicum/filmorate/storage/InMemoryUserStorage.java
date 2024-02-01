package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private static int lastId = 1;
    protected final List<User> list = new ArrayList<>();

    public User createUser(User user) {
        setNameByLogin(user);

        user.setId(generateId());
        list.add(user);
        log.debug("Проверки пройдены, пользователь создан");
        return user;
    }

    public User updateUser(User user) {
        setNameByLogin(user);
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

    private int generateId() {
        return lastId++;
    }

    private void setNameByLogin(User user) {
        if (user.getName().isBlank() || user.getName().isEmpty()) {
            log.warn("Имя пустое - будет использован логин");
            user.setName(user.getLogin());
        }
    }
}
