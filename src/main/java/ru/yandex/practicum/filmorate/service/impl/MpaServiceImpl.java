package ru.yandex.practicum.filmorate.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.MpaStorage;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Service
public class MpaServiceImpl implements MpaService {
    private final MpaStorage mpaStorage;

    @Override
    public Mpa readMpa(int id) {
        return mpaStorage.readMpa(id);
    }

    @Override
    public List<Mpa> readAllMpa() {
        return mpaStorage.readAllMpa();
    }
}
