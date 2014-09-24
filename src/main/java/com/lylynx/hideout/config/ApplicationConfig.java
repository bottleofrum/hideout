package com.lylynx.hideout.config;

import com.lylynx.hideout.admin.mvc.AdminConsoleController;
import com.lylynx.hideout.admin.mvc.AdminPartialsController;
import com.lylynx.hideout.admin.mvc.error.ErrorsBuilder;
import com.lylynx.hideout.apps.security.AccountController;
import com.lylynx.hideout.apps.security.GroupController;
import com.lylynx.hideout.apps.security.RoleController;
import com.lylynx.hideout.apps.security.WebAccessRuleController;
import com.lylynx.hideout.db.mongo.script.ScriptRunner;
import com.lylynx.hideout.db.mongo.script.ScriptRunnerRepository;
import com.lylynx.hideout.error.CustomErrorController;
import com.lylynx.hideout.home.HomeController;
import com.lylynx.hideout.signin.SigninController;
import com.lylynx.hideout.signup.SignupController;
import com.lylynx.hideout.spring.security.access.WebAccessRuleRepository;
import com.lylynx.hideout.spring.security.user.AccountRepository;
import com.lylynx.hideout.spring.security.user.GroupRepository;
import com.lylynx.hideout.spring.security.user.RoleRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.WebApplicationContext;

@Configuration
class ApplicationConfig {

    // CONTROLLERS
    @Bean
    public AdminConsoleController adminConsoleController() {
        return new AdminConsoleController();
    }

    @Bean
    public AdminPartialsController partialsController() {
        return new AdminPartialsController();
    }

    @Bean
    public HomeController homeController() {
        return new HomeController();
    }

    @Bean
    public SigninController signinController() {
        return new SigninController();
    }

    @Bean
    public SignupController signupController(final AccountRepository accountRepository, final UserDetailsService userDetailsService,
                                             final PasswordEncoder passwordEncoder) {
        return new SignupController(accountRepository, userDetailsService, passwordEncoder);
    }

    @Bean
    public AccountController accountController(ErrorsBuilder errorsBuilder, AccountRepository accountRepository,
                                               PasswordEncoder passwordEncoder) {
        return new AccountController(errorsBuilder, accountRepository, passwordEncoder);
    }

    @Bean
    public GroupController groupController(ErrorsBuilder errorsBuilder, GroupRepository groupRepository) {
        return new GroupController(errorsBuilder, groupRepository);
    }

    @Bean
    public RoleController roleController(ErrorsBuilder errorsBuilder, RoleRepository roleRepository) {
        return new RoleController(errorsBuilder, roleRepository);
    }

    @Bean
    public WebAccessRuleController webAccessRuleController(ErrorsBuilder errorsBuilder, WebAccessRuleRepository webAccessRuleRepository) {
        return new WebAccessRuleController(errorsBuilder, webAccessRuleRepository);
    }

    @Bean
    public CustomErrorController customErrorController() {
        return new CustomErrorController();
    }

    // SERVICES
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ErrorsBuilder errorsBuilder(MessageSource messageSource) {
        return new ErrorsBuilder(messageSource);
    }

    // MONGO SCRIPT RUNNER
    @Bean(initMethod = "init")
    public ScriptRunner scritptRunner(ScriptRunnerRepository scriptRunnerRepository, MongoDbFactory mongoDbFactory) {
        return new ScriptRunner(scriptRunnerRepository, mongoDbFactory);
    }
}
