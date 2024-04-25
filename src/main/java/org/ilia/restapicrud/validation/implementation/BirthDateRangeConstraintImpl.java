package org.ilia.restapicrud.validation.implementation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.ilia.restapicrud.dto.BirthDateRange;
import org.ilia.restapicrud.validation.annotation.BirthDateRangeConstraint;

public class BirthDateRangeConstraintImpl implements ConstraintValidator<BirthDateRangeConstraint, BirthDateRange> {

    @Override
    public boolean isValid(BirthDateRange birthDateRange, ConstraintValidatorContext context) {
        if (birthDateRange == null || birthDateRange.getFrom() == null || birthDateRange.getTo() == null)
            return false;
        return birthDateRange.getFrom().isBefore(birthDateRange.getTo());
    }
}
