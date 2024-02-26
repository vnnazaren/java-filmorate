package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.AfterDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

/**
 * Film.
 */
@Data
@AllArgsConstructor
public class Film {
    private List<Genre> genres;
    private Integer id;
    @NotNull(message = "The name cannot be empty")
    @NotBlank(message = "The name cannot be empty")
    private String name;
    @Size(min = 1, max = 200, message = "The maximum description length is 200 characters")
    private String description;
    @AfterDate(value = "1895-12-28", message = "Release date: no earlier than December 28, 1895")
    private LocalDate releaseDate;
    @Positive(message = "The length of the film must be positive")
    private Integer duration;
    private Mpa mpa;
}
