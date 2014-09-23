package com.lylynx.hideout.spring.security.user.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = UniqueRoleNameValidator.class)
public @interface UniqueRoleName {
    String message() default "{com.lylynx.hideout.spring.security.user.validators.UniqueRoleName.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
