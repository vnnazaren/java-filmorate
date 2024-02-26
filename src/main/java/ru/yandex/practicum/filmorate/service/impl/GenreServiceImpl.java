package ru.yandex.practicum.filmorate.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenreStorage;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreStorage genreStorage;

    @Override
    public Genre readGenre(int id) {
        return genreStorage.readGenre(id);
    }

    @Override
    public List<Genre> readGenres() {
        return genreStorage.readGenres();
    }
}
