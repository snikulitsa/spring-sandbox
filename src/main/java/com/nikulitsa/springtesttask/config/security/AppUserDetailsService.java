package com.nikulitsa.springtesttask.config.security;

import com.nikulitsa.springtesttask.entities.AppUser;
import com.nikulitsa.springtesttask.repositories.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Authenticate users from database
 */

@Component("userDetailsService")
public class AppUserDetailsService implements UserDetailsService {

    private final static Logger log = LoggerFactory.getLogger(AppUserDetailsService.class);

    private final AppUserRepository appUserRepository;

    @Autowired
    public AppUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username)
                .map(appUser -> {
                    log.debug("User exist");
                    return createSpringSecurityUser(appUser);
                })
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found."));
    }

    private User createSpringSecurityUser(AppUser appUser) {
        String username = appUser.getUsername();
        log.debug(username);
        String password = appUser.getPassword();
        log.debug(password);
        return new User(
                username,
                password,
                Collections.emptyList()
        );
    }
}
