package com.lylynx.hideout.spring.security.user;

import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: kuba
 * Date: 26.07.14
 * Time: 22:21
 */
public class AccountFactory {

    private PasswordEncoder passwordEncoder;

    public AccountFactory(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Account create(String username, String password, Collection<Group> groups,
                          Collection<GrantedAuthority> roles) {
        Account account = new Account();

        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        account.setLocked(false);
        account.setEnabled(true);
        account.setAccountCreationDate(new DateTime());
        account.setGroups(groups);
        account.setRoles(roles);

        return account;
    }
}
