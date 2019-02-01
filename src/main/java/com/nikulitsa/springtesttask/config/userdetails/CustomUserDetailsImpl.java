package com.nikulitsa.springtesttask.config.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetailsImpl extends User implements CustomUserDetails{

    private UserType userType;

    public CustomUserDetailsImpl(String username, String password,
                                 Collection<? extends GrantedAuthority> authorities) {

        super(username, password, authorities);
    }

    public CustomUserDetailsImpl(String username, String password,
                                 boolean enabled, boolean accountNonExpired,
                                 boolean credentialsNonExpired, boolean accountNonLocked,
                                 Collection<? extends GrantedAuthority> authorities) {
        super(
            username, password,
            enabled, accountNonExpired,
            credentialsNonExpired, accountNonLocked,
            authorities
        );
    }

    public UserType getUserType() {
        return userType;
    }

    public CustomUserDetailsImpl setUserType(UserType userType) {
        this.userType = userType;
        return this;
    }
}
