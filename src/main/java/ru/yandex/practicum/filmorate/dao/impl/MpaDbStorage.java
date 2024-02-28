package ru.yandex.practicum.filmorate.dao.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.MpaStorage;
import ru.yandex.practicum.filmorate.exception.MpaNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Repository("mpaDbStorage")
public class MpaDbStorage implements MpaStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Mpa readMpa(int id) {
        Mpa mpa;
        String sqlQuery = "select ID, NAME " +
                "from MPA where ID = ?";
        try {
            mpa = jdbcTemplate.queryForObject(sqlQuery, this::mapRowToMpa, id);
            log.info("В таблице MPA успешно выбрали запись с ID {}.", id);
        } catch (Exception e) {
            String warning = "Попытка выборки записи с ID " + id + " в таблице MPA." +
                    " Запрос: " + sqlQuery +
                    " Ошибка: " + e.getMessage();
            log.warn(warning);
            throw new MpaNotFoundException(warning);
        }

        return mpa;
    }

    @Override
    public List<Mpa> readAllMpa() {
        String sqlQuery = "select ID, NAME " +
                "from MPA";

        return jdbcTemplate.query(sqlQuery, this::mapRowToMpa);
    }

    private Mpa mapRowToMpa(ResultSet rs, int rowNum) throws SQLException {
        Integer id = rs.getInt("ID");
        String mpaName = rs.getString("NAME");

        return new Mpa(id, mpaName);
    }
}
