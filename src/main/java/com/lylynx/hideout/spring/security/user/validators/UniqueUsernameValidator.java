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
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, Account> {

    private static final String MESSAGE_CODE = "{com.lylynx.hideout.spring.security.user.validators.UniqueUsername.message}";
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void initialize(final UniqueUsername constraintAnnotation) {

    }

    @Override
    public boolean isValid(final Account entity, final ConstraintValidatorContext context) {
        final Account account = accountRepository.findOneByUsername(entity.getUsername());
        if(null == account || account.getId().equals(entity.getId())) {
            return true;
        }

        context.buildConstraintViolationWithTemplate(MESSAGE_CODE)
                .addNode("username").addConstraintViolation();

        return false;
    }
}
