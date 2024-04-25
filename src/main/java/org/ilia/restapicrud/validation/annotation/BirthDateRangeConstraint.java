package org.ilia.restapicrud.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.ilia.restapicrud.validation.implementation.BirthDateRangeConstraintImpl;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = BirthDateRangeConstraintImpl.class)
@Target(TYPE)
@Retention(RUNTIME)
public @interface BirthDateRangeConstraint {

    String message() default "\"from\" date must be before \"to\" date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
