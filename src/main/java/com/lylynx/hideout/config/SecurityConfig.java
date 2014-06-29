package com.lylynx.hideout.config;

import com.lylynx.hideout.account.AccountRepository;
import com.lylynx.hideout.account.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@Configuration
@ImportResource(value = "classpath:spring-security-context.xml")
class SecurityConfig {

    @Bean
    public UserService userService(AccountRepository accountRepository) {
        return new UserService(accountRepository);
    }

    @Bean
    public TokenBasedRememberMeServices rememberMeServices(UserService userService) {
        return new TokenBasedRememberMeServices("remember-me-key", userService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }

    @Profile("test")
    @Bean(name = "csrfMatcher")
    public RequestMatcher testCsrfMatcher() {
        return new RequestMatcher() {

            @Override
            public boolean matches(HttpServletRequest request) {
                return false;
            }
        };
    }

    @Profile("!test")
    @Bean(name = "csrfMatcher")
    public RequestMatcher csrfMatcher() {
        /**
         * Copy of default request matcher from
         * CsrfFilter$DefaultRequiresCsrfMatcher
         */
        return new RequestMatcher() {
            private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");

            /*
             * (non-Javadoc)
             * 
             * @see
             * org.springframework.security.web.util.matcher.RequestMatcher#
             * matches(javax.servlet.http.HttpServletRequest)
             */
            public boolean matches(HttpServletRequest request) {
                return !allowedMethods.matcher(request.getMethod()).matches();
            }
        };
    }
}
