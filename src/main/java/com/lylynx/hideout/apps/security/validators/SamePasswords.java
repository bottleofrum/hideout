package com.lylynx.hideout.apps.security.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.TYPE)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = SamePasswordsValidator.class)
public @interface SamePasswords {

    String message() default "{com.lylynx.hideout.apps.security.validators.SamePasswords.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
