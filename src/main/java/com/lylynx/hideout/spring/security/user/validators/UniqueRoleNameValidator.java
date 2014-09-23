package com.lylynx.hideout.spring.security.user.validators;

import com.lylynx.hideout.spring.security.user.HideoutGrantedAuthority;
import com.lylynx.hideout.spring.security.user.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueRoleNameValidator implements ConstraintValidator<UniqueRoleName, HideoutGrantedAuthority> {

    private static final String MESSAGE_CODE = "{com.lylynx.hideout.spring.security.user.validators.UniqueRoleName.message}";

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void initialize(UniqueRoleName constraintAnnotation) {

    }

    @Override
    public boolean isValid(HideoutGrantedAuthority entity, ConstraintValidatorContext context) {
        final HideoutGrantedAuthority role = roleRepository.findOneByAuthority(entity.getAuthority());
        if (null == role || role.getId().equals(entity.getId())) {
            return true;
        }

        context.buildConstraintViolationWithTemplate(MESSAGE_CODE)
                .addNode("authority").addConstraintViolation();

        return false;
    }
}
