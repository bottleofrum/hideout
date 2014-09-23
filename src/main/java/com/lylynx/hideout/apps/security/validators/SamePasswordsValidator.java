package com.lylynx.hideout.apps.security.validators;

import com.lylynx.hideout.apps.security.model.AccountDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 29.07.14
 * Time: 23:33
 */
public class SamePasswordsValidator implements ConstraintValidator<SamePasswords, AccountDTO> {

    public static final String MESSAGE_CODE = "{com.lylynx.hideout.apps.security.validators.SamePasswords.message}";

    @Override
    public void initialize(final SamePasswords constraintAnnotation) {
        //empty
    }

    @Override
    public boolean isValid(final AccountDTO value, final ConstraintValidatorContext context) {

        String password = value.getPassword();
        String password2 = value.getRepeatedPassword();

        if (null == password && null == password2) {
            return true;
        } else if (null == password || null == password2 || !password.equals(password2)) {
            context.buildConstraintViolationWithTemplate(MESSAGE_CODE)
                    .addNode("password").addConstraintViolation();
            context.buildConstraintViolationWithTemplate(MESSAGE_CODE)
                    .addNode("repeatedPassword").addConstraintViolation();

            return false;
        }

        return true;
    }
}
