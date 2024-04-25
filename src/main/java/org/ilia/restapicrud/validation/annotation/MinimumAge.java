package org.ilia.restapicrud.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.ilia.restapicrud.validation.implementation.MinimumAgeImpl;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = MinimumAgeImpl.class)
@Target(FIELD)
@Retention(RUNTIME)
public @interface MinimumAge {

    String message() default "You are too young";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
