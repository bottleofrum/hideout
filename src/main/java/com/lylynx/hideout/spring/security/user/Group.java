package com.lylynx.hideout.spring.security.user;

import com.lylynx.hideout.config.Constants;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Document(collection = Constants.SECURITY_GROUPS)
public class Group {

    private final String id;
    private final String name;
    private final Collection<GrantedAuthority> authorities;

    @JsonCreator
    public Group(@JsonProperty("id") final String id, @JsonProperty("name") final String name,
            @JsonProperty("authorities") @JsonDeserialize(contentAs = HideoutGrantedAuthority.class) final
            Collection<GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.authorities = authorities;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getName() {
        return name;
    }
}
