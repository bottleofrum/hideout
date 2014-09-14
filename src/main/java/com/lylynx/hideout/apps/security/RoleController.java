package com.lylynx.hideout.apps.security;

import com.lylynx.hideout.admin.mvc.crud.CrudController;
import com.lylynx.hideout.admin.mvc.error.ErrorsBuilder;
import com.lylynx.hideout.spring.security.user.HideoutGrantedAuthority;
import com.lylynx.hideout.spring.security.user.RoleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/r/security/role")
public class RoleController extends CrudController<HideoutGrantedAuthority> {

    public RoleController(final ErrorsBuilder errorsBuilder, RoleRepository repository) {
        super(errorsBuilder, repository);
    }
}
