package com.lylynx.hideout.spring.security.user.validators;

import com.lylynx.hideout.spring.security.user.Account;
import com.lylynx.hideout.spring.security.user.Group;
import com.lylynx.hideout.spring.security.user.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueGroupNameValidator implements ConstraintValidator<UniqueGroupName, Group> {

    private static final String MESSAGE_CODE = "{com.lylynx.hideout.spring.security.user.validators.UniqueGroupName.message}";

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public void initialize(UniqueGroupName constraintAnnotation) {

    }

    @Override
    public boolean isValid(Group entity, ConstraintValidatorContext context) {

        final Group group = groupRepository.findOneByName(entity.getName());
        if(null == group || group.getId().equals(entity.getId())) {
            return true;
        }

        context.buildConstraintViolationWithTemplate(MESSAGE_CODE)
                .addNode("name").addConstraintViolation();

        return false;
    }
}
