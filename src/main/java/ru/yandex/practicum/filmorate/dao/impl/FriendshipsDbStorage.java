package ru.yandex.practicum.filmorate.dao.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.FriendshipsStorage;

@Slf4j
@AllArgsConstructor
@Repository("friendshipsDbStorage")
public class FriendshipsDbStorage implements FriendshipsStorage {
    JdbcTemplate jdbcTemplate;

    @Override
    public void createFriend(int userId, int friendId) {
        String sqlQuery = "insert into FRIENDSHIPS (USER_ID, FRIEND_ID) " +
                "values (?, ?)";
        if (jdbcTemplate.update(sqlQuery, userId, friendId) <= 0)
            log.info("В таблице FRIENDSHIPS создана запись для USER_ID - {}, FRIEND_ID - {}.",
                    userId, friendId);
        else
            log.warn("Попытка создания записи в таблице FRIENDSHIPS. Запрос: {}. USER_ID - {}. FRIEND_ID 2 - {}.",
                    sqlQuery, userId, friendId);
    }

    @Override
    public void deleteFriend(int userId, int friendId) {
        String sqlQuery = "delete from FRIENDSHIPS " +
                "where USER_ID = ? AND FRIEND_ID = ?";

        if (jdbcTemplate.update(sqlQuery, userId, friendId) <= 0)
            log.info("В таблице FRIENDSHIPS удалена запись с USER_ID - {}, FRIEND_ID - {}.",
                    userId, friendId);
        else
            log.warn("Попытка удаления записи в таблице FRIENDSHIPS. Запрос: {}. USER_ID - {}. FRIEND_ID 2 - {}.",
                    sqlQuery, userId, friendId);

    }
}
