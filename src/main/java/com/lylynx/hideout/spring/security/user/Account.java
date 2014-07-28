package com.lylynx.hideout.spring.security.user;

import com.lylynx.hideout.config.Constants;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@SuppressWarnings("serial")
@Document(collection = Constants.SECURITY_USERS)
public class Account {

    private String id;

    private String username;

    @JsonIgnore
    private String password;

    private boolean locked;

    private boolean enabled;

    private DateTime accountCreationDate;

    private DateTime accountExpirationDate;

    private DateTime credentialsExpirationDate;

    private Collection<GrantedAuthority> roles;

    private Collection<Group> groups;

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(final boolean locked) {
        this.locked = locked;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public DateTime getAccountExpirationDate() {
        return accountExpirationDate;
    }

    public void setAccountExpirationDate(final DateTime accountExpirationDate) {
        this.accountExpirationDate = accountExpirationDate;
    }

    public DateTime getCredentialsExpirationDate() {
        return credentialsExpirationDate;
    }

    public void setCredentialsExpirationDate(final DateTime credentialsExpirationDate) {
        this.credentialsExpirationDate = credentialsExpirationDate;
    }

    public DateTime getAccountCreationDate() {
        return accountCreationDate;
    }

    public void setAccountCreationDate(final DateTime accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }

    public boolean isAccountNonExpired() {
        if(null == accountExpirationDate) {
            return true;
        }

        return accountExpirationDate.isAfterNow();
    }

    public boolean isCredentialsNonExpired() {
        if(null == credentialsExpirationDate) {
            return true;
        }

        return credentialsExpirationDate.isAfterNow();
    }

    public Collection<GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(final Collection<GrantedAuthority> roles) {
        this.roles = roles;
    }

    public Collection<Group> getGroups() {
        return groups;
    }

    public void setGroups(final Collection<Group> groups) {
        this.groups = groups;
    }
}
