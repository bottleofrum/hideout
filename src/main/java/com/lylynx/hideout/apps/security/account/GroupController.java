package com.lylynx.hideout.apps.security.account;

import com.lylynx.hideout.admin.mvc.CrudController;
import com.lylynx.hideout.spring.security.user.Group;
import com.lylynx.hideout.spring.security.user.GroupRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/r/security/group")
public class GroupController extends CrudController<Group>{

    public GroupController(final GroupRepository repository) {
        super(repository);
    }
}
