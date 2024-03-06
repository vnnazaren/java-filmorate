package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    /**
     * Создание пользователя
     */
    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    /**
     * Получение всех пользователей
     */
    @GetMapping
    public List<User> readUsers() {
        return userService.readUsers();
    }

    /**
     * Получение пользователя
     */
    @GetMapping("/{id}")
    public User readUser(@PathVariable("id") int id) {
        return userService.readUser(id);
    }

    /**
     * Изменение пользователя
     */
    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }

    /**
     * Добавление в друзья
     */
    @PutMapping("/{id}/friends/{friendId}")
    public void createFriend(
            @PathVariable("id") int id,
            @PathVariable("friendId") int friendId) {
        userService.createFriend(id, friendId);
    }

    /**
     * Удаление из друзей
     */
    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(
            @PathVariable("id") int id,
            @PathVariable("friendId") int friendId) {
        userService.deleteFriend(id, friendId);
    }

    /**
     * Список друзей пользователя
     */
    @GetMapping("/{id}/friends")
    public List<User> readFriends(
            @PathVariable("id") int id) {
        return userService.readUserFriends(id);
    }

    /**
     * Список общих друзей
     */
    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> readCommonFriends(
            @PathVariable("id") int id,
            @PathVariable("otherId") int otherId) {
        return userService.readCommonFriends(id, otherId);
    }
}
