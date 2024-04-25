package org.ilia.restapicrud.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ValidateObject {

    private final Validator validator;

    public void validate(Object objectForValidation) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(objectForValidation);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
