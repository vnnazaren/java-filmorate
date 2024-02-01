package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IncorrectParametersException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
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

//    public Set<User> getUserFriends(int userId) {
//
//        Set<Integer> ids = userStorage.getUserById(userId).getFriends();
//        List<User> users = userStorage.getUsers();
//
//        List<User> qwerty = ids.stream()
//                .map(id -> users.stream()
//                        .filter(user -> id.equals(user.getId()))
//                        .findAny()
//                        .orElseThrow(() -> new UserNotFoundException(
//                                String.format("Пользователь c id - %s не найден", userId))))
//                .collect(Collectors.toList());
//
//        SortedSet<User> sortedSet = new TreeSet<>((u1, u2) -> Integer.compare(u1.getId(), u2.getId()));
//        sortedSet.addAll(qwerty);
//
//        return sortedSet;
//    }


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

        SortedSet<User> sortedSet = new TreeSet<>((u1, u2) -> Integer.compare(u1.getId(), u2.getId()));
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
}
