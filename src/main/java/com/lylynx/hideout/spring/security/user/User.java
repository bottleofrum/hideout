package com.lylynx.hideout.spring.security.user;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;

public class User extends org.springframework.security.core.userdetails.User {

    private static Collection<? extends GrantedAuthority> mergeAuthorities(final Collection<? extends GrantedAuthority> roles,
                                                                           final Collection<? extends Group> groups) {
        final Collection<GrantedAuthority> authorities = new HashSet<>();
        
        if(null != roles) {
            authorities.addAll(roles);
        }
        if(null != groups) {
            for (Group group : groups) {
                authorities.addAll(group.getAuthorities());
            }
        }
        
        return authorities;
    }

    private final Collection<? extends Group> groups;
    private final Collection<? extends GrantedAuthority> roles;

    public User(final String username, final String password, final boolean enabled, final boolean accountNonExpired,
            final boolean credentialsNonExpired, final boolean accountNonLocked,
            final Collection<? extends GrantedAuthority> roles, final Collection<? extends Group> groups) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
                mergeAuthorities(roles, groups));
        this.groups = groups;
        this.roles = roles;
    }

    public Collection<? extends GrantedAuthority> getRoles() {
        return roles;
    }

    public Collection<? extends Group> getGroups() {
        return groups;
    }
}
