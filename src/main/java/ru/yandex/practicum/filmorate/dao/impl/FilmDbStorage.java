package ru.yandex.practicum.filmorate.dao.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.FilmStorage;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
@Repository("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film createFilm(Film film) {
        String sqlQuery = "insert into FILMS (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA) " +
                "values (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
                stmt.setString(1, film.getName());
                stmt.setString(2, film.getDescription());
                stmt.setString(3, film.getReleaseDate().toString());
                stmt.setString(4, film.getDuration().toString());
                stmt.setString(5, film.getMpa().getId().toString());
                return stmt;
            }, keyHolder);
            film.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
            log.info("В таблице FILM успешно создана запись с ID {} и названием {}.",
                    film.getId(), film.getName());
        } catch (Exception e) {
            log.warn("Попытка создания записи в таблице FILM. Запрос: {}. Ошибка: {}.",
                    sqlQuery, e.getMessage());
        }

        return film;
    }

    @Override
    public Film readFilm(int id) {
        Film film;
        String sqlQuery = "select ID, NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA " +
                "from FILMS " +
                "where ID = ?";
        try {
            film = jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilm, id);
            log.info("В таблице FILM успешно выбрали запись с id {}.", id);
        } catch (Exception e) {
            String warning = "Попытка выборки записи с id " + id + " в таблице FILM. " +
                    "Запрос: " + sqlQuery
                    + "Ошибка: " + e.getMessage();
            log.warn(warning);
            throw new FilmNotFoundException(warning);
        }
        return film;
    }

    @Override
    public List<Film> readFilms() {
        String sqlQuery = "select ID, NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA " +
                "from FILMS";

        log.info("Выборка записей из таблицы FILMS.");
        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
    }

    @Override
    public List<Film> readTopFilms(int sizeList) {
        String sqlQuery = "select f.ID, f.NAME, f.DESCRIPTION, f.RELEASE_DATE, f.DURATION, f.MPA " +
                "from FILMS as f " +
                "left join LIKES L on f.ID = L.FILM_ID " +
                "group by f.ID " +
                "order by count(l.USER_ID) desc " +
                "limit " + sizeList;

        log.info("Выборка топ-фильмов из таблицы FILMS.");
        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
    }

    @Override
    public Film updateFilm(Film film) {
        String sqlQuery = "update FILMS set NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, MPA = ? " +
                "where ID = ?";
        int resultUpdate = jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());

        if (resultUpdate <= 0) {
            String warning = "Попытка обновления записи в таблице FILMS. " +
                    "Запрос: " + sqlQuery;
            log.warn(warning);
            throw new FilmNotFoundException(warning);
        }
        log.info("В таблице FILM обновление записи с ID {} и именем {}.",
                film.getId(), film.getName());
        return film;
    }

    private Film mapRowToFilm(ResultSet rs, int rowNum) throws SQLException {
        Integer id = rs.getInt("ID");
        String filmName = rs.getString("NAME");
        String description = rs.getString("DESCRIPTION");
        LocalDate releaseDate = rs.getDate("RELEASE_DATE").toLocalDate();
        Integer duration = rs.getInt("DURATION");

        return new Film(null, id, filmName, description, releaseDate, duration, null);
    }
}
