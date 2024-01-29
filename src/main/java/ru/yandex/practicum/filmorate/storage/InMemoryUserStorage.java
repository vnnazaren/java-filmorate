package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private static int lastId = 0;
    protected final List<User> list = new ArrayList<>();

    public User create(User user) {
        setNameByLogin(user);

        user.setId(generateId());
        list.add(user);
        log.debug("Проверки пройдены, пользователь создан");
        return user;
    }

    public User update(User user) throws ValidationException {
        setNameByLogin(user);

        boolean isUserExist = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == user.getId()) {
                list.set(i, user);
                isUserExist = true;
            }
        }

        if (!isUserExist) throw new ValidationException("Нет пользователя с ID " + user.getId());

        return user;
    }

    public List<User> read() {
        return list;
    }

    private int generateId() {
        return ++lastId;
    }

    private void setNameByLogin(User user) {
        if (user.getName() == null) {
            log.warn("Имя пустое - будет использован логин");
            user.setName(user.getLogin());
        }
    }
}
