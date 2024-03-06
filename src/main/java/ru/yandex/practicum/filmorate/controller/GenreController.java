package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/genres")
public class GenreController {
    private GenreService genreService;

    /**
     * Получение жанра по ID
     */
    @GetMapping("/{id}")
    public Genre readGenre(@PathVariable("id") int id) {
        return genreService.readGenre(id);
    }

    /**
     * Получение всех жанров
     */
    @GetMapping
    public List<Genre> readGenres() {
        return genreService.readGenres();
    }
}
