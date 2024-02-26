package ru.yandex.practicum.filmorate.dao;

public interface LikesStorage {

    int createLike(Integer filmId, Integer userId);

    int deleteLike(Integer filmId, Integer userId);
}
