package com.lylynx.hideout.apps.security.account;

import com.lylynx.hideout.admin.mvc.CrudController;
import com.lylynx.hideout.spring.security.user.Account;
import com.lylynx.hideout.spring.security.user.AccountRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/r/security/account")
public class AccountController extends CrudController<Account>{


    public AccountController(final AccountRepository repository) {
        super(repository);
    }
}
