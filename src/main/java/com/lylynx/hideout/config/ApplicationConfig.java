package com.lylynx.hideout.config;

import com.lylynx.hideout.account.AccountController;
import com.lylynx.hideout.account.AccountRepository;
import com.lylynx.hideout.account.UserService;
import com.lylynx.hideout.admin.mvc.AdminConsoleController;
import com.lylynx.hideout.admin.mvc.PartialsController;
import com.lylynx.hideout.error.CustomErrorController;
import com.lylynx.hideout.home.HomeController;
import com.lylynx.hideout.signin.SigninController;
import com.lylynx.hideout.signup.SignupController;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class ApplicationConfig {

    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocation(new ClassPathResource("/persistence.properties"));
        return ppc;
    }

    // CONTROLLERS
    @Bean
    public AdminConsoleController adminConsoleController() {
        return new AdminConsoleController();
    }

    @Bean
    public PartialsController partialsController() {
        return new PartialsController();
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
    public SignupController signupController(final AccountRepository accountRepository, final UserService userService,
                                             final PasswordEncoder passwordEncoder) {
        return new SignupController(accountRepository, userService, passwordEncoder);
    }

    @Bean
    public AccountController accountController(AccountRepository accountRepository) {
        return new AccountController(accountRepository);
    }

    @Bean
    public CustomErrorController customErrorController() {
        return new CustomErrorController();
    }

    // SERVICES
    @Bean
    public UserService userService(final AccountRepository accountRepository) {
        return new UserService(accountRepository);
    }

}
