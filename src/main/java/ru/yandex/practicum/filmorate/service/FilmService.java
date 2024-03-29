package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {

    Film createFilm(Film film);

    Film readFilm(int id);

    Film updateFilm(Film film);

    List<Film> readFilms();

    int createLike(int filmId, int userId);

    int deleteLike(int filmId, int userId);

    List<Film> readTopFilms(int sizeList);
}
