package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addLike(int filmId, int userId) {
        userStorage.getUserById(userId);
        filmStorage.getFilmById(filmId).getLikes().add(userId);
    }

    public void deleteLike(int filmId, int userId) {
        userStorage.getUserById(userId);
        filmStorage.getFilmById(filmId).getLikes().remove(userId);
    }

    public List<Film> getTopFilms(int sizeList) {
        return filmStorage.getAllFilms().stream()
                .sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size())
                .limit(sizeList)
                .collect(Collectors.toList());
    }
}
