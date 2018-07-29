package app.services;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Andrey on 29.07.2018.
 */
public class GrantedAuthorityImpl implements GrantedAuthority {
    private String authority;

    GrantedAuthorityImpl(String role) {
        this.authority = role;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
