package com.lylynx.hideout.spring.security.user.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = UniqueGroupNameValidator.class)
public @interface UniqueGroupName {
    String message() default "{com.lylynx.hideout.spring.security.user.validators.UniqueGroupName.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default {};
}
