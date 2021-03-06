package com.lylynx.hideout.apps.security;

import com.lylynx.hideout.admin.mvc.crud.CreateGroup;
import com.lylynx.hideout.admin.mvc.crud.CrudController;
import com.lylynx.hideout.admin.mvc.crud.UpdateGroup;
import com.lylynx.hideout.admin.mvc.error.ErrorsBuilder;
import com.lylynx.hideout.apps.security.model.AccountDTO;
import com.lylynx.hideout.config.Constants;
import com.lylynx.hideout.spring.security.user.Account;
import com.lylynx.hideout.spring.security.user.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import javax.validation.groups.Default;

@Controller
@RequestMapping("/r/security/account")
public class AccountController extends CrudController<Account> {

    private final PasswordEncoder passwordEncoder;

    public AccountController(final ErrorsBuilder errorsBuilder, final AccountRepository repository, PasswordEncoder passwordEncoder) {
        super(errorsBuilder, repository);
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "", produces = Constants.MIME_TYPE_JSON, method = RequestMethod.POST,
            consumes = Constants.MIME_TYPE_JSON)
    public ResponseEntity create(@Validated(value = {CreateGroup.class, Default.class}) @RequestBody AccountDTO entity) {

        entity.setPassword(passwordEncoder.encode(entity.getPassword()));

        getRepository().save(entity.toAccount());
        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    public ResponseEntity update(@PathVariable String id, @Validated(value = {UpdateGroup.class, Default.class}) @RequestBody Account entity) {
        Account account = getRepository().findOne(id);
        entity.setPassword(account.getPassword());
        return super.update(id, entity);
    }

    @Override
    @RequestMapping(value = "", produces = Constants.MIME_TYPE_JSON, method = RequestMethod.POST,
            consumes = Constants.MIME_TYPE_JSON, params = "implementation=super")
    public ResponseEntity create(@Valid @RequestBody final Account entity) {
        // do nothing
        return new ResponseEntity(HttpStatus.OK);
    }
}
