package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IncorrectParametersException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    private static int lastId = 1;
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User createUser(User user) {
        setNameByLogin(user);
        user.setId(generateId());
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        setNameByLogin(user);
        return userStorage.updateUser(user);
    }

    public List<User> getUsers() {
        return userStorage.getUsers();
    }

    public User getUserById(int id) {
        return userStorage.getUserById(id);
    }

    public void addFriend(int userId, int friendId) {
        if (userId == friendId)
            throw new IncorrectParametersException("id", "friendId");

        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);

        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    public void removeFriend(int userId, int friendId) {
        if (userId == friendId)
            throw new IncorrectParametersException("id", "friendId");

        User user = userStorage.getUserById(userId);
        User friend = userStorage.getUserById(friendId);

        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    public Set<User> getUserFriends(int userId) {

        Set<Integer> ids = userStorage.getUserById(userId).getFriends();
        List<User> users = userStorage.getUsers();

        Set<User> result = ids.stream()
                .map(id -> users.stream()
                        .filter(user -> id.equals(user.getId()))
                        .findAny()
                        .orElseThrow(() -> new UserNotFoundException(
                                String.format("Пользователь c id - %s не найден", userId))))
                .collect(Collectors.toSet());

        SortedSet<User> sortedSet = new TreeSet<>(Comparator.comparingInt(User::getId));
        sortedSet.addAll(result);

        return sortedSet;
    }

    public Set<User> getCommonFriends(int firstUserId, int secondUserId) {
        Set<Integer> list = userStorage.getUserById(firstUserId).getFriends();
        Set<Integer> otherList = userStorage.getUserById(secondUserId).getFriends();

        return userStorage.getUsers().stream()
                .filter(user -> list.contains(user.getId()) && otherList.contains(user.getId()))
                .collect(Collectors.toSet());
    }

    private int generateId() {
        return lastId++;
    }

    private void setNameByLogin(User user) {
        if (user.getName() == null
                || user.getName().isBlank()
                || user.getName().isEmpty()) {
            log.warn("Имя пустое - будет использован логин");
            user.setName(user.getLogin());
        }
    }
}
