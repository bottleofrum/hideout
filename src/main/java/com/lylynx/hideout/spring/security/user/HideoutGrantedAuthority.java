package com.lylynx.hideout.spring.security.user;


import com.lylynx.hideout.config.Constants;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Document(collection = Constants.SECURITY_ROLES)
public class HideoutGrantedAuthority implements GrantedAuthority{

    private String id;
    private String role;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setAuthority(final String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
