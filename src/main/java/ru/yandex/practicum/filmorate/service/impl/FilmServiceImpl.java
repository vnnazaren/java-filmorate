package ru.yandex.practicum.filmorate.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.*;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.IncorrectParametersException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final MpaStorage mpaStorage;
    private final LikesStorage likesStorage;
    private final GenreStorage genreStorage;
    private final FilmsGenresStorage filmsGenresStorage;

    @Override
    public Film createFilm(Film film) {
        Film createdFilm = filmStorage.createFilm(film);
        createdFilm.setMpa(mpaStorage.readMpa(film.getMpa().getId()));
        createdFilm.setGenres(createGenresListByFilm(film));

        return createdFilm;
    }

    @Override
    public Film readFilm(int id) {
        return filmStorage.readFilm(id);
    }

    @Override
    public List<Film> readFilms() {
        return filmStorage.readFilms();
    }

    @Override
    public List<Film> readTopFilms(int sizeList) {
        return filmStorage.readTopFilms(sizeList);
    }

    @Override
    public Film updateFilm(Film film) {
        if (filmStorage.readFilm(film.getId()) == null)
            throw new IncorrectParameterException("id");

        Film updatedFilm = filmStorage.updateFilm(film);
        updatedFilm.setMpa(mpaStorage.readMpa(film.getMpa().getId()));
        updatedFilm.setGenres(createGenresListByFilm(film));

        return updatedFilm;
    }

    @Override
    public int createLike(int filmId, int userId) {
        if (filmStorage.readFilm(filmId) == null
                || userStorage.readUser(userId) == null)
            throw new IncorrectParametersException("userId", "friendId");

        return likesStorage.createLike(filmId, userId);
    }

    @Override
    public int deleteLike(int filmId, int userId) {
        if (filmStorage.readFilm(filmId) == null
                || userStorage.readUser(userId) == null)
            throw new IncorrectParametersException("userId", "friendId");

        return likesStorage.deleteLike(filmId, userId);
    }

    private List<Genre> createGenresListByFilm(Film film) {
        List<Genre> result = new ArrayList<>();

        List<Genre> genres = film.getGenres();
        if (genres != null) {
            genres = genres.stream()
                    .sorted(Comparator.comparingInt(Genre::getId))
                    .distinct()
                    .collect(Collectors.toList());
            filmsGenresStorage.deleteFilmsGenresByFilmId(film.getId());
            for (Genre genre : genres) { // todo: Удалить цикл
                result.add(genreStorage.readGenre(genre.getId()));
                filmsGenresStorage.createFilmsGenres(film.getId(), genre.getId());
            }
        }

        return result;
    }
}
