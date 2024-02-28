package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

public interface FilmsGenresStorage {

    void createFilmsGenres(Film film);

    void deleteFilmsGenresByFilmId(Integer filmId);
}
