package app.services;

import app.models.Role;
import app.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * Created by Andrey on 29.07.2018.
 */
public class MyUserPrincipal implements UserDetails {
    private User user;

    public MyUserPrincipal(final User user) {
        this.user = user;
    }

    public MyUserPrincipal() {
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        final Set<GrantedAuthority> grntdAuths = new HashSet<GrantedAuthority>();

        List<Role> roleList = null;

        if (user != null) {
            roleList = new ArrayList<>();
            roleList.add(user.getRole());
        }

        if (roleList != null) {
            for (Role role : roleList) {
                grntdAuths.add(new GrantedAuthorityImpl(role.getRole()));
            }
        }

        return grntdAuths;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        if (this.user == null) {
            return null;
        }
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        //To do
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //To do
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //To do
        return true;
    }

    @Override
    public boolean isEnabled() {
        //To do
        return true;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "MyUserPrincipal [user=" + user + "]";
    }
}