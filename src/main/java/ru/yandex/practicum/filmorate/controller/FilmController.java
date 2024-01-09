package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private static int lastId = 0;
    protected final List<Film> list = new ArrayList<>();

    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        checkFilmReleaseDate(film);

        film.setId(generateId());
        list.add(film);
        log.debug("Проверки пройдены, фильм создан");
        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) throws ValidationException {
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

    @GetMapping
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
