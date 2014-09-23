package com.lylynx.hideout.spring.security.user;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.PostConstruct;

public class HideoutUserDetailsService implements UserDetailsService {

    private AccountRepository accountRepository;

    public HideoutUserDetailsService(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostConstruct
    protected void initialize() {
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findOneByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return createUser(account);
    }

    public void signin(Account account) {
        SecurityContextHolder.getContext().setAuthentication(authenticate(account));
    }

    private Authentication authenticate(Account account) {
        User user = createUser(account);

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    private User createUser(Account account) {
        return new com.lylynx.hideout.spring.security.user.User(account.getUsername(), account.getPassword(),
                account.isEnabled(), account.isAccountNonExpired(), account.isCredentialsNonExpired(),
                !account.isLocked(), account.getRoles(), account.getGroups());
    }


}
