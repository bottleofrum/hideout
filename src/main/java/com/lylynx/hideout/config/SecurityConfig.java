package com.lylynx.hideout.config;

import com.lylynx.hideout.account.AccountRepository;
import com.lylynx.hideout.account.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
@EnableWebMvcSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String REMEMBER_ME_KEY = "remember-me-key";

    @Autowired
    private AccountRepository accountRepository;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/favicon.ico", "/resources/**", "/", "/signup*", "/signin*","/generalError").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/signin").failureUrl("/signin?error=1")
                .and().rememberMe()
                .rememberMeServices(new TokenBasedRememberMeServices(REMEMBER_ME_KEY,
                        userService())).key(REMEMBER_ME_KEY);
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(true).userDetailsService(userService());
    }

    // SERVICES
    @Bean
    public UserService userService() {
        return new UserService(accountRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }

}
