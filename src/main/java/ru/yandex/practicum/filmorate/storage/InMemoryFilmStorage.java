package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private static int lastId = 0;
    protected final List<Film> list = new ArrayList<>();

    public Film create(Film film) throws ValidationException {
        checkFilmReleaseDate(film);

        film.setId(generateId());
        list.add(film);
        log.debug("Проверки пройдены, фильм создан");
        return film;
    }

    public Film update(Film film) throws ValidationException {
        checkFilmReleaseDate(film);

        boolean isFilmExist = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == film.getId()) {
                list.set(i, film);
                isFilmExist = true;
            }
        }

        if (!isFilmExist) throw new ValidationException("Нет фильма с ID " + film.getId());

        return film;
    }

    public List<Film> read() {
        return list;
    }

    private int generateId() {
        return ++lastId;
    }

    private void checkFilmReleaseDate(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.error("Дата релиза — не раньше 28 декабря 1895 года");
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года");
        }
    }
}
