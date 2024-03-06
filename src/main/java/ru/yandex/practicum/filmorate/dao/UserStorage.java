package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User createUser(User user);

    User readUser(int id);

    List<User> readUsers();

    List<User> readUserFriends(int userId);

    User updateUser(User user);
}
