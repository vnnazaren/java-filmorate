package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private static int lastId = 1;
    protected final List<Film> list = new ArrayList<>();

    public Film createFilm(Film film) throws ValidationException {
        checkFilmReleaseDate(film);

        film.setId(generateId());
        list.add(film);
        log.debug("Проверки пройдены, фильм создан");
        return film;
    }

    public Film updateFilm(Film film) throws ValidationException {
        checkFilmReleaseDate(film);

        Film userToUpdate = getFilmById(film.getId());
        list.set(list.indexOf(userToUpdate), film);

        return film;
    }

    public List<Film> getAllFilms() {
        return list;
    }

    public Film getFilmById(int id) {
        return list.stream()
                .filter(film -> film.getId() == id)
                .findFirst()
                .orElseThrow(() -> new FilmNotFoundException(String.format("Фильм c id - %s не найден", id)));
    }

    private int generateId() {
        return lastId++;
    }

    private void checkFilmReleaseDate(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("Дата релиза — не раньше 28 декабря 1895 года");
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }
    }
}
