package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    User readUser(int id);

    List<User> readUsers();

    User updateUser(User user);

    void createFriend(int userId, int friendId);

    void deleteFriend(int userId, int friendId);

    List<User> readUserFriends(int userId);

    List<User> readCommonFriends(int firstUserId, int secondUserId);
}
