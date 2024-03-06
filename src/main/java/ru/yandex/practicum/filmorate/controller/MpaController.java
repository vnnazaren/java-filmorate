package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/mpa")
public class MpaController {
    private MpaService mpaService;

    /**
     * Получение MPA по ID
     */
    @GetMapping("/{id}")
    public Mpa readMpa(@PathVariable("id") int id) {
        return mpaService.readMpa(id);
    }

    /**
     * Получение всех MPA
     */
    @GetMapping
    public List<Mpa> readAllMpa() {
        return mpaService.readAllMpa();
    }
}
