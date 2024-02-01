package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.IncorrectParametersException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static java.util.Comparator.comparing;

@Slf4j
@RestController
@RequestMapping
public class UserController {
    public final UserService userService;
    private final UserStorage userStorage;

    @Autowired
    public UserController(UserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }

    @PostMapping("/users")
    public User create(@Valid @RequestBody User user) {
        return userStorage.createUser(user);
    }

    @PutMapping("/users")
    public User update(@Valid @RequestBody User user) throws ValidationException {
        return userStorage.updateUser(user);
    }

    @GetMapping("/users")
    public List<User> read() {
        return userStorage.getUsers();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") int id) {
        return userStorage.getUserById(id);
    }

    /**
     * Добавление в друзья
     */
    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriend(
            @PathVariable("id") int id,
            @PathVariable("friendId") int friendId) {

        userService.addFriend(id, friendId);
    }

    /**
     * Удаление из друзей
     */

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriend(
            @PathVariable("id") int id,
            @PathVariable("friendId") int friendId) {
        userService.removeFriend(id, friendId);
    }

    /**
     * Список друзей пользователя
     */
    @GetMapping("/users/{id}/friends")
    public Set<User> getFriends(
            @PathVariable("id") int id) {
        return userService.getUserFriends(id);
    }

    /**
     * Список общих друзей
     */
    @GetMapping("/users/{id}/friends/common/{otherId}")
    public Set<User> getCommonFriends(
            @PathVariable("id") int id,
            @PathVariable("otherId") int otherId) {
        return userService.getCommonFriends(id, otherId);
    }
}
