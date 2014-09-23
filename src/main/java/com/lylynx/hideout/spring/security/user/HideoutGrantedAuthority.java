package com.lylynx.hideout.spring.security.user;


import com.lylynx.hideout.config.Constants;
import com.lylynx.hideout.spring.security.user.validators.UniqueRoleName;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Document(collection = Constants.SECURITY_ROLES)
@JsonDeserialize
@UniqueRoleName
public class HideoutGrantedAuthority implements GrantedAuthority {

    @Id
    private String id;

    @Indexed(unique = true)
    @NotEmpty
    private String authority;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(final String role) {
        this.authority = role;
    }
}
