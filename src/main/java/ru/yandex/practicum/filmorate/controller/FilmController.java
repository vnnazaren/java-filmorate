package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    /**
     * Создание фильма
     */
    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return filmService.createFilm(film);
    }

    /**
     * Изменение фильма
     */
    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    /**
     * Получение всех фильмов
     */
    @GetMapping
    public List<Film> read() {
        return filmService.getFilms();
    }

    /**
     * Получение фильма по ID
     */
    @GetMapping("/{id}")
    public Film getFilm(@PathVariable("id") int id) {
        return filmService.getFilmById(id);
    }

    /**
     * Пользователь ставит лайк фильму
     */
    @PutMapping("/{id}/like/{userId}")
    public void addLike(
            @PathVariable("id") int id,
            @PathVariable("userId") int userId) {
        filmService.addLike(id, userId);
    }

    /**
     * Пользователь удаляет лайк
     */
    @DeleteMapping("/{id}/like/{userId}")
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
    @GetMapping("/popular")
    public List<Film> getTopFilms(
            @RequestParam(value = "count", defaultValue = "10", required = false) Integer count) {
        return filmService.getTopFilms(count);
    }
}
