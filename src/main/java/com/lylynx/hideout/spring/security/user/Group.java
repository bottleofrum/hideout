package com.lylynx.hideout.spring.security.user;

import com.lylynx.hideout.config.Constants;
import com.lylynx.hideout.spring.security.user.validators.UniqueGroupName;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Document(collection = Constants.SECURITY_GROUPS)
@UniqueGroupName
public class Group {

    @Id
    private final String id;

    @Indexed(unique = true)
    @NotEmpty
    private final String name;

    @NotEmpty
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

    public String getId() {
        return id;
    }
}
