package ru.yandex.practicum.filmorate.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IncorrectParametersException extends RuntimeException {
    private final String parameter1;
    private final String parameter2;
}
