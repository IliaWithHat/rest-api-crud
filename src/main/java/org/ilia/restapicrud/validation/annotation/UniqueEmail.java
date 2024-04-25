package org.ilia.restapicrud.validation.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.ilia.restapicrud.validation.implementation.UniqueEmailImpl;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = UniqueEmailImpl.class)
@Target(TYPE)
@Retention(RUNTIME)
public @interface UniqueEmail {

    String message() default "User with this email already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
