package ru.yandex.practicum.filmorate.dao;

public interface FilmsGenresStorage {

    void createFilmsGenres(Integer filmId, Integer genreId);

    void deleteFilmsGenresByFilmId(Integer filmId);
}
