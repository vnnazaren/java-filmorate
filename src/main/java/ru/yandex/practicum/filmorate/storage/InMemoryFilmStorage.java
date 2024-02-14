package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    protected final List<Film> list = new ArrayList<>();

    public Film createFilm(Film film) {
        list.add(film);
        return film;
    }

    public Film updateFilm(Film film) {
        Film filmToUpdate = getFilmById(film.getId());
        list.set(list.indexOf(filmToUpdate), film);
        return film;
    }

    public List<Film> getFilms() {
        return list;
    }

    public Film getFilmById(int id) {
        return list.stream()
                .filter(film -> film.getId() == id)
                .findFirst()
                .orElseThrow(() -> new FilmNotFoundException(String.format("Фильм c id - %s не найден", id)));
    }
}
