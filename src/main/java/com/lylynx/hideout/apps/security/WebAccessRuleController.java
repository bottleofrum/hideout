package com.lylynx.hideout.apps.security;

import com.lylynx.hideout.admin.mvc.crud.CrudController;
import com.lylynx.hideout.admin.mvc.error.ErrorsBuilder;
import com.lylynx.hideout.spring.security.access.WebAccessRule;
import com.lylynx.hideout.spring.security.access.WebAccessRuleRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/r/security/webaccessrule")
public class WebAccessRuleController extends CrudController<WebAccessRule> {

    public WebAccessRuleController(ErrorsBuilder errorsBuilder, WebAccessRuleRepository webAccessRuleRepository) {
        super(errorsBuilder, webAccessRuleRepository);
    }
}
