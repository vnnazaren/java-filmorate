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
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
        String sqlQuery = "select f.ID,\n" +
                "       f.NAME,\n" +
                "       f.DESCRIPTION,\n" +
                "       f.RELEASE_DATE,\n" +
                "       f.DURATION,\n" +
                "       mp.ID                     as mpa_id,\n" +
                "       mp.NAME                   as mpa_name,\n" +
                "       GROUP_CONCAT(fg.GENRE_ID) as genres_id,\n" +
                "       GROUP_CONCAT(gn.NAME)     as genres_name\n" +
                "from FILMS as f\n" +
                "         left join FILMS_GENRES fg on f.ID = fg.FILM_ID\n" +
                "         left join MPA mp on f.MPA = mp.ID\n" +
                "         left join GENRES gn on fg.GENRE_ID = gn.ID\n" +
                "where f.id = ?\n" +
                "group by f.ID";
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
        String sqlQuery = "select f.ID,\n" +
                "       f.NAME,\n" +
                "       f.DESCRIPTION,\n" +
                "       f.RELEASE_DATE,\n" +
                "       f.DURATION,\n" +
                "       mp.ID                     as mpa_id,\n" +
                "       mp.NAME                   as mpa_name,\n" +
                "       GROUP_CONCAT(fg.GENRE_ID) as genres_id,\n" +
                "       GROUP_CONCAT(gn.NAME)     as genres_name\n" +
                "from FILMS as f\n" +
                "         left join FILMS_GENRES fg on f.ID = fg.FILM_ID\n" +
                "         left join MPA mp on f.MPA = mp.ID\n" +
                "         left join GENRES gn on fg.GENRE_ID = gn.ID\n" +
                "group by f.ID";

        log.info("Выборка записей из таблицы FILMS.");
        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
    }

    @Override
    public List<Film> readTopFilms(int sizeList) {
        String sqlQuery = "select f.ID,\n" +
                "       f.NAME,\n" +
                "       f.DESCRIPTION,\n" +
                "       f.RELEASE_DATE,\n" +
                "       f.DURATION,\n" +
                "       mp.ID                     as mpa_id,\n" +
                "       mp.NAME                   as mpa_name,\n" +
                "       GROUP_CONCAT(fg.GENRE_ID) as genres_id,\n" +
                "       GROUP_CONCAT(gn.NAME)     as genres_name\n" +
                "from FILMS as f\n" +
                "         left join MPA mp on f.MPA = mp.ID\n" +
                "         left join FILMS_GENRES fg on f.ID = fg.FILM_ID\n" +
                "         left join GENRES gn on fg.GENRE_ID = gn.ID\n" +
                "         left join LIKES L on f.ID = L.FILM_ID\n" +
                "group by f.ID\n" +
                "order by count(l.USER_ID) desc\n" +
                "limit ?";

        log.info("Выборка топ-фильмов из таблицы FILMS в количестве {} штук.",
                sizeList);
        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm, sizeList);
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
        Mpa mpa = new Mpa(rs.getInt("MPA_ID"),
                rs.getString("MPA_NAME"));
        List<Genre> genres = getGenresForFilm(rs.getString("GENRES_ID"),
                rs.getString("GENRES_NAME"));

        return new Film(id, filmName, description, releaseDate, duration, mpa, genres);
    }

    private List<Genre> getGenresForFilm(String genreIds, String genreNames) {
        List<Genre> result = new ArrayList<>();
        if (genreIds != null && genreNames != null) {
            String[] ids = genreIds.split(",");
            String[] names = genreNames.split(",");
            for (int i = 0; i < ids.length; i++) {
                result.add(new Genre(Integer.parseInt(ids[i]), names[i]));
            }
        }

        return result;
    }

}
