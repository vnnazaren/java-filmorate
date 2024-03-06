package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film createFilm(Film film);

    Film readFilm(int id);

    List<Film> readFilms();

    List<Film> readTopFilms(int sizeList);

    Film updateFilm(Film film);
}
