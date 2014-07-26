package com.lylynx.hideout.config;

import com.lylynx.hideout.admin.mvc.AdminConsoleController;
import com.lylynx.hideout.admin.mvc.AdminPartialsController;
import com.lylynx.hideout.spring.security.user.AccountRepository;
import com.lylynx.hideout.db.mongo.script.ScriptRunner;
import com.lylynx.hideout.db.mongo.script.ScriptRunnerRepository;
import com.lylynx.hideout.error.CustomErrorController;
import com.lylynx.hideout.home.HomeController;
import com.lylynx.hideout.signin.SigninController;
import com.lylynx.hideout.signup.SignupController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    public SignupController signupController(final AccountRepository accountRepository,
                                             final UserDetailsService userDetailsService,
                                             final PasswordEncoder passwordEncoder) {
        return new SignupController(accountRepository, userDetailsService, passwordEncoder);
    }

    @Bean
    public CustomErrorController customErrorController() {
        return new CustomErrorController();
    }

    // SERVICES

    //MONGO SCRIPT RUNNER
    @Bean(initMethod = "init")
    public ScriptRunner scritptRunner(ScriptRunnerRepository scriptRunnerRepository, MongoDbFactory mongoDbFactory) {
        return new ScriptRunner(scriptRunnerRepository, mongoDbFactory);
    }
}
