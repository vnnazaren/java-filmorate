package ru.yandex.practicum.filmorate.storage;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public User createUser(User user) {
        String sqlQuery = "insert into \"user\" " +
                "(\"user_name\", \"user_login\", \"user_email\", \"user_birthday\") " +
                "values (?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sqlQuery
                    , user.getName()
                    , user.getLogin()
                    , user.getEmail()
                    , user.getBirthday());
            log.info("В таблице \"user\" успешно создана запись с id {} и именем {}.", user.getId(), user.getName());
        } catch (Exception e) {
            log.warn("Попытка создания записи в таблице \"user\". Запрос: {}. Ошибка: {}.", sqlQuery, e.getMessage());
        }
        return user;
    }

    @Override
    public User updateUser(User user) {
        String sqlQuery = "update \"user\" set " +
                "\"user_name\" = ?, \"user_login\" = ?, \"user_email\" = ?, \"user_birthday\" = ?" +
                "where \"id\" = ?";

        int resultUpdate = jdbcTemplate.update(sqlQuery
                , user.getName()
                , user.getLogin()
                , user.getEmail()
                , user.getBirthday()
                , user.getId());
        if (resultUpdate <= 0) {
            String warning = "Попытка обновления записи в таблице \"user\". Запрос: \n" + sqlQuery;
            log.warn(warning);
            throw new UserNotFoundException(warning);
        }
        log.info("В таблице \"user\" успешно обновлена запись с id {} и именем {}.", user.getId(), user.getName());
        return user;
    }

    @Override
    public List<User> getUsers() {


        return null;
    }

    @Override
    public User getUserById(int id) {
        User user = null;
        String sqlQuery = "select \"id\", \"user_name\", \"user_login\", \"user_email\", \"user_birthday\"" +
                "from \"user\" where \"id\" = ?";
        try {
            user = jdbcTemplate.queryForObject(sqlQuery, this::mapRowToUser, id);
            log.info("В таблице \"user\" успешно выбрали запись с id {}.", id);
        } catch (Exception e) {
            log.warn("Попытка выборки записи с id {} в таблице \"user\". Запрос: {}. Ошибка: {}.", id, sqlQuery, e.getMessage());
        }
        return user;
    }

    private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        Integer id = rs.getInt("id");
        String userName = rs.getString("user_name");
        String userLogin = rs.getString("user_login");
        String userEmail = rs.getString("user_email");
        LocalDate userBirthday = rs.getDate("user_birthday").toLocalDate();

        return new User(id, userName, userLogin, userEmail, userBirthday);
    }
}
