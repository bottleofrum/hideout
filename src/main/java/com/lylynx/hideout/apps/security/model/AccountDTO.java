package com.lylynx.hideout.apps.security.model;

import com.lylynx.hideout.apps.security.validators.SamePasswords;
import com.lylynx.hideout.spring.security.user.Account;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

@SamePasswords
public class AccountDTO extends Account {

    @NotEmpty
    private String repeatedPassword;

    @Override
    @JsonIgnore(false)
    public String getPassword() {
        return super.getPassword();
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(final String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public Account toAccount() {
        Account account = new Account();
        account.setAccountCreationDate(getAccountCreationDate());
        account.setAccountExpirationDate(getAccountExpirationDate());
        account.setCredentialsExpirationDate(getCredentialsExpirationDate());
        account.setEnabled(isEnabled());
        account.setGroups(getGroups());
        account.setLocked(isLocked());
        account.setRoles(getRoles());
        account.setPassword(getPassword());
        account.setUsername(getUsername());
        return account;
    }
}
