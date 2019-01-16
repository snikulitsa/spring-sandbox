package com.nikulitsa.springtesttask.config.security;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component("ldapUserDetailsService")
public class LdapUserDetailsService implements UserDetailsContextMapper {

    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx,
                                          String username,
                                          Collection<? extends GrantedAuthority> authorities) {

        //TODO this is mock
        return new User(username, "Qwerty123!", Collections.emptyList());
    }

    @Override
    public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {

    }
}
