package ru.yandex.practicum.filmorate.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class AfterDateValidator implements ConstraintValidator<AfterDate, LocalDate> {
    private LocalDate date;

    @Override
    public void initialize(AfterDate constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        date = LocalDate.parse(constraintAnnotation.value());
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext context) {
        return !localDate.isBefore(date);
    }
}
