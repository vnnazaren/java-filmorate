package ru.yandex.practicum.filmorate.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FriendshipsStorage;
import ru.yandex.practicum.filmorate.dao.UserStorage;
import ru.yandex.practicum.filmorate.exception.IncorrectParametersException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;
    private final FriendshipsStorage friendshipsStorage;

    @Override
    public User createUser(User user) {
        setNameByLogin(user);
        return userStorage.createUser(user);
    }

    @Override
    public User readUser(int id) {
        return userStorage.readUser(id);
    }

    @Override
    public List<User> readUsers() {
        return userStorage.readUsers();
    }

    @Override
    public User updateUser(User user) {
        setNameByLogin(user);
        return userStorage.updateUser(user);
    }

    @Override
    public void createFriend(int userId, int friendId) {
        if (userId == friendId
                || userStorage.readUser(userId) == null
                || userStorage.readUser(friendId) == null)
            throw new IncorrectParametersException("userId", "friendId");

        friendshipsStorage.createFriend(userId, friendId);
    }

    @Override
    public void deleteFriend(int userId, int friendId) {
        if (userId == friendId
                || userStorage.readUser(userId) == null
                || userStorage.readUser(friendId) == null)
            throw new IncorrectParametersException("userId", "friendId");

        friendshipsStorage.deleteFriend(userId, friendId);
    }

    @Override
    public List<User> readUserFriends(int userId) {

        return userStorage.readUserFriends(userId);
    }

    @Override
    public List<User> readCommonFriends(int firstUserId, int secondUserId) {
        List<User> list = userStorage.readUserFriends(firstUserId);
        List<User> otherList = userStorage.readUserFriends(secondUserId);

        list.retainAll(otherList);

        return list;
    }

    private void setNameByLogin(User user) {
        if (user.getName() == null || user.getName().isBlank() || user.getName().isEmpty()) {
            log.warn("Имя пустое - будет использован логин");
            user.setName(user.getLogin());
        }
    }
}
