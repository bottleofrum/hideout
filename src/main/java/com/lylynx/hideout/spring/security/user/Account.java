package com.lylynx.hideout.spring.security.user;

import com.lylynx.hideout.admin.mvc.crud.CreateGroup;
import com.lylynx.hideout.config.Constants;
import com.lylynx.hideout.spring.security.user.validators.UniqueUsername;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@SuppressWarnings("serial")
@Document(collection = Constants.SECURITY_USERS)
@UniqueUsername
public class Account {

    @Id
    private String id;

    @Indexed(unique = true)
    @NotEmpty
    private String username;

    @NotEmpty(groups = CreateGroup.class)
    private String password;

    private boolean locked;

    private boolean enabled;

    @NotNull
    private DateTime accountCreationDate;

    private DateTime accountExpirationDate;

    private DateTime credentialsExpirationDate;

    @JsonDeserialize(contentAs = HideoutGrantedAuthority.class)
    private Collection<GrantedAuthority> roles;

    private Collection<Group> groups;

    public String getId() {
        return id;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
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

    @JsonIgnore
    public boolean isAccountNonExpired() {
        if (null == accountExpirationDate) {
            return true;
        }

        return accountExpirationDate.isAfterNow();
    }

    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        if (null == credentialsExpirationDate) {
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
