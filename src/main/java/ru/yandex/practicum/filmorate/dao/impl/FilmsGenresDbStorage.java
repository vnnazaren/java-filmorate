package ru.yandex.practicum.filmorate.dao.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.FilmsGenresStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Repository("filmsGenresDbStorage")
public class FilmsGenresDbStorage implements FilmsGenresStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void createFilmsGenres(Film film) {
        String sqlQuery = "insert into FILMS_GENRES (FILM_ID, GENRE_ID) " +
                "values (?, ?)";
        if (film.getGenres() != null) {
            Integer filmId = film.getId();
            List<Genre> genres = film.getGenres();
            jdbcTemplate.batchUpdate(sqlQuery, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, filmId);
                    ps.setInt(2, genres.get(i).getId());
                }

                @Override
                public int getBatchSize() {
                    return genres.size();
                }
            });
        }
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
