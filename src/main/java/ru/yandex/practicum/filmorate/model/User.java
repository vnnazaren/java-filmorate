package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * User.
 */
@Data
@AllArgsConstructor
public class User {
    private Integer id;
    @Email(message = "Email cannot be empty and must contain the @ symbol")
    private String email;
    @NotBlank(message = "Login cannot be empty")
    @Pattern(regexp = "^\\S+$", message = "Login cannot contain spaces")
    private String login;
    private String name;
    @PastOrPresent(message = "The date of birth cannot be in the future")
    private LocalDate birthday;
    private final Set<Integer> friends = new HashSet<>();
}
