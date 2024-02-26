package ru.yandex.practicum.filmorate.dao.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.UserStorage;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
@Repository("userDbStorage")
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User createUser(User user) {
        String sqlQuery = "insert into USERS (NAME, LOGIN, EMAIL, BIRTHDAY) " +
                "values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getLogin());
                stmt.setString(3, user.getEmail());
                stmt.setString(4, user.getBirthday().toString());
                return stmt;
            }, keyHolder);
            user.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
            log.info("В таблице USERS успешно создана запись с ID {} и именем {}.",
                    user.getId(), user.getName());
        } catch (Exception e) {
            log.warn("Попытка создания записи в таблице USERS. Запрос: {}. ID - {}. Имя - {}. Ошибка: {}.",
                    sqlQuery, user.getId(), user.getName(), e.getMessage());
        }

        return user;
    }

    @Override
    public User readUser(int id) {
        User user;
        String sqlQuery = "select ID, NAME, LOGIN, EMAIL, BIRTHDAY from USERS where ID = ?";
        try {
            user = jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, id);
            log.info("В таблице USERS успешно выбрали запись с ID {}.", id);
        } catch (Exception e) {
            String warning = "Попытка выборки записи с ID " + id + "в таблице USERS." +
                    " Запрос: " + sqlQuery +
                    " Ошибка: " + e.getMessage();
            log.warn(warning);
            throw new UserNotFoundException(warning);
        }

        return user;
    }

    @Override
    public List<User> readUsers() {
        String sqlQuery = "select ID, NAME, LOGIN, EMAIL, BIRTHDAY from USERS";

        return jdbcTemplate.query(sqlQuery, this::mapRowToUser);
    }

    @Override
    public List<User> readUserFriends(int userId) {
        String sqlQuery = "select u.ID, u.NAME, u.LOGIN, u.EMAIL, u.BIRTHDAY " +
                "from USERS as u " +
                "right join FRIENDSHIPS as fs on u.id = fs.FRIEND_ID " +
                "where fs.USER_ID = ?" +
                "order by u.ID";
        List<User> users = jdbcTemplate.query(sqlQuery, this::mapRowToUser, userId);
        log.info("Успешно получили друзей USERS с ID {}.", userId);

        return users;
    }

    @Override
    public User updateUser(User user) {
        String sqlQuery = "update USERS set NAME = ?, LOGIN = ?, EMAIL = ?, BIRTHDAY = ? where ID = ?";

        int resultUpdate = jdbcTemplate.update(sqlQuery,
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday(),
                user.getId());

        if (resultUpdate <= 0) {
            String warning = "Попытка обновления записи в таблице USERS. Запрос: " + sqlQuery;
            log.warn(warning);
            throw new UserNotFoundException(warning);
        }
        log.info("В таблице USERS обновление записи с ID {} и именем {}.", user.getId(), user.getName());

        return user;
    }

    private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        Integer id = rs.getInt("ID");
        String userName = rs.getString("NAME");
        String userLogin = rs.getString("LOGIN");
        String userEmail = rs.getString("EMAIL");
        LocalDate userBirthday = rs.getDate("BIRTHDAY").toLocalDate();

        return new User(id, userName, userLogin, userEmail, userBirthday);
    }
}
