package ru.yandex.practicum.filmorate.dao.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.FilmsGenresStorage;

@Slf4j
@AllArgsConstructor
@Repository("filmsGenresDbStorage")
public class FilmsGenresDbStorage implements FilmsGenresStorage {
    private final JdbcTemplate jdbcTemplate;

    public void createFilmsGenres(Integer filmId, Integer genreId) {
        String sqlQuery = "insert into FILMS_GENRES (FILM_ID, GENRE_ID) " +
                "values (?, ?)";

        if (jdbcTemplate.update(sqlQuery, filmId, genreId) <= 0)
            log.warn("Попытка создания записи в таблице FILMS_GENRES. Запрос: {}. ID фильма - {}. ID жанра - {}.",
                    sqlQuery, filmId, genreId);
        else
            log.info("В таблице FILMS_GENRES успешно создана запись для фильма с ID {} и жанра с ID {}",
                    filmId, genreId);
    }

    @Override
    public void deleteFilmsGenresByFilmId(Integer filmId) {
        String sqlQuery = "delete from FILMS_GENRES where FILM_ID = ?";

        if (jdbcTemplate.update(sqlQuery, filmId) <= 0)
            log.warn("Попытка удаления записей по ID фильма ({}) в таблице FILMS_GENRES. Запрос: {}.",
                    filmId, sqlQuery);
        else
            log.info("В таблице FILMS_GENRES успешно удалены записи с FILM_ID - {}.",
                    filmId);
    }
}
