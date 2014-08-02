package com.lylynx.hideout.spring.security.user.validators;

import com.lylynx.hideout.spring.security.user.Account;
import com.lylynx.hideout.spring.security.user.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 02.08.14
 * Time: 17:57
 */
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void initialize(final UniqueUsername constraintAnnotation) {

    }

    @Override
    public boolean isValid(final String username, final ConstraintValidatorContext context) {
        final Account account = accountRepository.findOneByUsername(username);
        if(null == account) {
            return true;
        }
        return false;
    }
}
