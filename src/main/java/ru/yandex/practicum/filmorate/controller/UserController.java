package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    protected final List<User> list = new ArrayList<>();

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        if (user.getName() == null) {
            log.warn("Имя пустое - будет использован логин");
            user.setName(user.getLogin());
        }

        user.setId(user.generateId());
        list.add(user);
        log.debug("Проверки пройдены, пользователь создан");
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) throws ValidationException {
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

    @GetMapping
    public List<User> read() {
        return list;
    }
}
