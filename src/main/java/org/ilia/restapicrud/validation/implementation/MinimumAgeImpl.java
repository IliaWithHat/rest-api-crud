package org.ilia.restapicrud.validation.implementation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.ilia.restapicrud.validation.annotation.MinimumAge;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MinimumAgeImpl implements ConstraintValidator<MinimumAge, LocalDate> {

    @Value("${user.minimum-age:18}")
    private int minAge;

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        if (birthDate == null)
            return false;
        return minAge <= ChronoUnit.YEARS.between(birthDate, LocalDate.now());
    }
}
