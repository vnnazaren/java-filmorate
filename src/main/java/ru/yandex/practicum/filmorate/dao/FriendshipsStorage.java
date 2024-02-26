package ru.yandex.practicum.filmorate.dao;

public interface FriendshipsStorage {

    void createFriend(int userId, int friendId);

    void deleteFriend(int userId, int friendId);
}
