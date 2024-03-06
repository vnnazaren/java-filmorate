package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

/**
 * User.
 */
@Data
@AllArgsConstructor
public class User {
    private Integer id;
    private String name;
    @NotBlank(message = "Login cannot be empty")
    @Pattern(regexp = "^\\S+$", message = "Login cannot contain spaces")
    private String login;
    @Email(message = "Email cannot be empty and must contain the @ symbol")
    private String email;
    @PastOrPresent(message = "The date of birth cannot be in the future")
    private LocalDate birthday;
}
