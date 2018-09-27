package app.security;

import app.models.User;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Andrey on 29.07.2018.
 */
public class UserToUserDetails {

    @Bean
    private static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static UserDetails convert(User user) {
        UserDetailsImpl userDetails = new UserDetailsImpl();

        userDetails.setUsername(user.getName());
        userDetails.setPassword(passwordEncoder().encode(user.getPassword()));
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getRole()));
        userDetails.setAuthorities(authorities);

        return userDetails;
    }
}