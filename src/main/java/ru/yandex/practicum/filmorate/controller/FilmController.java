package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    /**
     * Создание фильма
     */
    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        return filmService.createFilm(film);
    }

    /**
     * Получение всех фильмов
     */
    @GetMapping
    public List<Film> readFilms() {
        return filmService.readFilms();
    }

    /**
     * Получение фильма по ID
     */
    @GetMapping("/{id}")
    public Film readFilm(@PathVariable("id") int id) {
        return filmService.readFilm(id);
    }

    /**
     * Изменение фильма
     */
    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    /**
     * Пользователь ставит лайк фильму
     */
    @PutMapping("/{id}/like/{userId}")
    public int createLike(
            @PathVariable("id") int id,
            @PathVariable("userId") int userId) {
        return filmService.createLike(id, userId);
    }

    /**
     * Пользователь удаляет лайк
     */
    @DeleteMapping("/{id}/like/{userId}")
    public int deleteLike(
            @PathVariable("id") int id,
            @PathVariable("userId") int userId) {
        return filmService.deleteLike(id, userId);
    }

    /**
     * Возвращает список из первых count фильмов по количеству
     * лайков. <br/> Если значение параметра count не задано,
     * верните первые 10
     */
    @GetMapping("/popular")
    public List<Film> getTopFilms(
            @RequestParam(value = "count", defaultValue = "10", required = false) Integer count) {
        return filmService.readTopFilms(count);
    }
}
