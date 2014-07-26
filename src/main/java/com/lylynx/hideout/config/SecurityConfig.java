package com.lylynx.hideout.config;

import com.lylynx.hideout.spring.security.access.MongoFilterInvocationSecurityMetadataSource;
import com.lylynx.hideout.spring.security.access.WebAccessRuleRepository;
import com.lylynx.hideout.spring.security.user.AccountRepository;
import com.lylynx.hideout.spring.security.user.HideoutUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvcSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String REMEMBER_ME_KEY = "remember-me-key";

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private FilterSecurityInterceptor filterSecurityInterceptor;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.addFilter(filterSecurityInterceptor).formLogin().loginPage("/signin").failureUrl("/signin?error=1").and().rememberMe()
                .rememberMeServices(new TokenBasedRememberMeServices(REMEMBER_ME_KEY, userDetailsService())).key(REMEMBER_ME_KEY);
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.eraseCredentials(true).userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Bean
    public MongoFilterInvocationSecurityMetadataSource mongoFilterInvocationSecurityMetadataSource(WebAccessRuleRepository webAccessRuleRepository) {
        return new MongoFilterInvocationSecurityMetadataSource(webAccessRuleRepository);
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter> decisionVoters = new ArrayList<>();
        decisionVoters.add(new WebExpressionVoter());
        decisionVoters.add(new RoleVoter());
        decisionVoters.add(new AuthenticatedVoter());
        return new AffirmativeBased(decisionVoters);
    }

    @Bean
    public FilterSecurityInterceptor filterSecurityInterceptor(MongoFilterInvocationSecurityMetadataSource securityMetadataSource) throws Exception {
        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
        filterSecurityInterceptor.setSecurityMetadataSource(securityMetadataSource);
        filterSecurityInterceptor.setAuthenticationManager(authenticationManager());
        filterSecurityInterceptor.setAccessDecisionManager(accessDecisionManager());

        return filterSecurityInterceptor;
    }

    // SERVICES
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HideoutUserDetailsService userDetailsService() {
        return new HideoutUserDetailsService(accountRepository);
    }

}
