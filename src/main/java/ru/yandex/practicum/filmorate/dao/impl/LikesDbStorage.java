package ru.yandex.practicum.filmorate.dao.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.LikesStorage;

@Slf4j
@AllArgsConstructor
@Repository("likesDbStorage")
public class LikesDbStorage implements LikesStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public int createLike(Integer filmId, Integer userId) {
        String sqlQuery = "insert into LIKES (FILM_ID, USER_ID) " +
                "values (?, ?)";

        int result = jdbcTemplate.update(sqlQuery, filmId, userId);
        if (result <= 0)
            log.warn("Попытка создания записи в таблице LIKES. Запрос: {}. ID фильма - {}. ID пользователя - {}.",
                    sqlQuery, filmId, userId);
        else
            log.info("В таблице LIKES успешно создана запись для фильма с ID {} и пользователя с ID {}",
                    filmId, userId);
        return result;
    }

    @Override
    public int deleteLike(Integer filmId, Integer userId) {
        String sqlQuery = "delete from LIKES " +
                "where FILM_ID = ? AND USER_ID = ?";

        int result = jdbcTemplate.update(sqlQuery, filmId, userId);
        if (result <= 0)
            log.warn("Попытка удаления записи в таблице LIKES. Запрос: {}.", sqlQuery);
        else
            log.info("В таблице LIKES успешно удалена запись с FILM_ID - {} и USER_ID - {}.", filmId, userId);
        return result;
    }
}
