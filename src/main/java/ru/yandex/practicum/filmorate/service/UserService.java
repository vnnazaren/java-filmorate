package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    User createUser(User user);

    User updateUser(User user);

    List<User> getUsers();

    User getUserById(int id);

    void addFriend(int userId, int friendId);

    void removeFriend(int userId, int friendId);

    Set<User> getUserFriends(int userId);

    Set<User> getCommonFriends(int firstUserId, int secondUserId);
}
