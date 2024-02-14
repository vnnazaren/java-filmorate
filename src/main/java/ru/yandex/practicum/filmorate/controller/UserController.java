package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Создание пользователя
     */
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    /**
     * Изменение пользователя
     */
    @PutMapping
    public User update(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }

    /**
     * Получение всех пользователей
     */
    @GetMapping
    public List<User> read() {
        return userService.getUsers();
    }

    /**
     * Получение пользователя
     */
    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }

    /**
     * Добавление в друзья
     */
    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(
            @PathVariable("id") int id,
            @PathVariable("friendId") int friendId) {

        userService.addFriend(id, friendId);
    }

    /**
     * Удаление из друзей
     */
    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(
            @PathVariable("id") int id,
            @PathVariable("friendId") int friendId) {
        userService.removeFriend(id, friendId);
    }

    /**
     * Список друзей пользователя
     */
    @GetMapping("/{id}/friends")
    public Set<User> getFriends(
            @PathVariable("id") int id) {
        return userService.getUserFriends(id);
    }

    /**
     * Список общих друзей
     */
    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<User> getCommonFriends(
            @PathVariable("id") int id,
            @PathVariable("otherId") int otherId) {
        return userService.getCommonFriends(id, otherId);
    }
}
