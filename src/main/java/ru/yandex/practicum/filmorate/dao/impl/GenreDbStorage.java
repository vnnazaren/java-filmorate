package ru.yandex.practicum.filmorate.dao.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.GenreStorage;
import ru.yandex.practicum.filmorate.exception.GenreNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Repository("genreDbStorage")
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> readGenresByFilm(Integer filmId) {
        String sqlQuery = "select g.ID, g.NAME " +
                "FROM FILMS_GENRES as fg " +
                "LEFT JOIN GENRES as g on fg.GENRE_ID = g.ID " +
                "WHERE fg.FILM_ID =  ?";

        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre, filmId);
    }

    @Override
    public Genre readGenre(int id) {
        Genre genre;
        String sqlQuery = "select ID, NAME " +
                "from GENRES where ID = ?";
        try {
            genre = jdbcTemplate.queryForObject(sqlQuery, this::mapRowToGenre, id);
            log.info("В таблице GENRE успешно выбрали запись с ID {}.", id);
        } catch (Exception e) {
            String warning = "Попытка выборки записи с ID " + id + " в таблице GENRE. " +
                    "Запрос:" + sqlQuery +
                    "Ошибка:" + e.getMessage();
            log.warn(warning);
            throw new GenreNotFoundException(warning);
        }
        return genre;
    }

    @Override
    public List<Genre> readGenres() {
        String sqlQuery = "select ID, NAME " +
                "from GENRES";

        List<Genre> genres = jdbcTemplate.query(sqlQuery, this::mapRowToGenre);

        log.info("Выборка записей из таблицы GENRE.");
        return genres;
    }

    private Genre mapRowToGenre(ResultSet rs, int rowNum) throws SQLException {
        Integer id = rs.getInt("ID");
        String genreName = rs.getString("NAME");
        return new Genre(id, genreName);
    }
}
