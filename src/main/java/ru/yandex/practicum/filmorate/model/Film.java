package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

/**
 * Film.
 */
@Data
@AllArgsConstructor
public class Film {
    private int id;
    @NotNull(message = "The name cannot be empty")
    @NotBlank(message = "The name cannot be empty")
    private String name;
    @Size(min = 0, max = 200, message = "The maximum description length is 200 characters")
    private String description;
    // дата релиза — не раньше 28 декабря 1895 года - проверка реализована в контроллере
    private LocalDate releaseDate;
    @Positive(message = "The length of the film must be positive")
    private int duration;
}
