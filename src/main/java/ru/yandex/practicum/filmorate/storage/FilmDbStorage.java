package ru.yandex.practicum.filmorate.storage;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Film createFilm(Film film) {
        String sqlQuery = "insert into \"film\" " +
                "(\"film_name\", \"description\", \"release_date\", \"duration\", \"age_rating\") " +
                "values (?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sqlQuery
                    , film.getName()
                    , film.getDescription()
                    , film.getReleaseDate()
                    , film.getDuration()
                    , film.getAgeRating());
            log.info("В таблице \"film\" успешно создана запись с id {} и названием {}.", film.getId(), film.getName());
        } catch (Exception e) {
            log.warn("Попытка создания записи в таблице \"film\". Запрос: {}. Ошибка: {}.", sqlQuery, e.getMessage());
        }
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        String sqlQuery = "update \"film\" set " +
                "\"film_name\" = ?, \"description\" = ?, \"release_date\" = ?, \"duration\" = ?, \"age_rating\" = ?" +
                "where \"id\" = ?";
        int resultUpdate = jdbcTemplate.update(sqlQuery
                , film.getName()
                , film.getDescription()
                , film.getReleaseDate()
                , film.getDuration()
                , film.getAgeRating()
                , film.getId());

        if (resultUpdate <= 0) {
            String warning = "Попытка обновления записи в таблице \"film\". Запрос:\n" + sqlQuery;
            log.warn(warning);
            throw new FilmNotFoundException(warning);
        }

        log.info("В таблице \"film\" успешно обновлена запись с id {} и названием {}.", film.getId(), film.getName());
        return film;
    }

    @Override
    public List<Film> getFilms() {

        return null;
    }

    @Override
    public Film getFilmById(int id) {
        Film film = null;
        String sqlQuery = "select \"id\", \"film_name\", \"description\", \"release_date\", \"duration\", \"age_rating\"" +
                "from \"film\" where \"id\" = ?";
        try {
            film = jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilm, id);
            log.info("В таблице \"film\" успешно выбрали запись с id {}.", id);
        } catch (Exception e) {
            log.warn("Попытка выборки записи с id {} в таблице \"film\". Запрос: {}. Ошибка: {}.", id, sqlQuery, e.getMessage());
        }
        return film;
    }

    private Film mapRowToFilm(ResultSet rs, int rowNum) throws SQLException {
        Integer id = rs.getInt("id");
        String filmName = rs.getString("film_name");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("creation_date").toLocalDate();
        Integer duration = rs.getInt("duration");
        String ageRating = rs.getString("ageRating");

        return new Film(id, filmName, description, releaseDate, duration, ageRating);
    }
}
