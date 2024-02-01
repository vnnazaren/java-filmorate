package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
public class FilmController {
    private final FilmStorage filmStorage;
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
    }

    @PostMapping("/films")
    public Film create(@Valid @RequestBody Film film) throws ValidationException {
        return filmStorage.createFilm(film);
    }

    @PutMapping("/films")
    public Film update(@Valid @RequestBody Film film) throws ValidationException {
        return filmStorage.updateFilm(film);
    }

    @GetMapping("/films")
    public List<Film> read() {
        return filmStorage.getAllFilms();
    }

    @GetMapping("/films/{id}")
    public Film getUser(@PathVariable("id") int id) {
        return filmStorage.getFilmById(id);
    }

    /**
     * Пользователь ставит лайк фильму
     */
    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(
            @PathVariable("id") int id,
            @PathVariable("userId") int userId) {
        filmService.addLike(id, userId);
    }

    /**
     * Пользователь удаляет лайк
     */
    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(
            @PathVariable("id") int id,
            @PathVariable("userId") int userId) {
        filmService.deleteLike(id, userId);
    }

    /**
     * Возвращает список из первых count фильмов по количеству
     * лайков. <br/> Если значение параметра count не задано,
     * верните первые 10
     */
    @GetMapping("/films/popular")
    public List<Film> getTopFilms(
            @RequestParam(value = "count", defaultValue = "10", required = false) Integer count) {
        return filmService.getTopFilms(count);
    }

}
