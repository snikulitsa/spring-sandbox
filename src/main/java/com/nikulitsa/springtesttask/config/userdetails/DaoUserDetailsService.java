package com.nikulitsa.springtesttask.config.userdetails;

import com.nikulitsa.springtesttask.entities.AppUser;
import com.nikulitsa.springtesttask.repositories.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Authenticate users from database
 */

@Component("userDetailsService")
public class DaoUserDetailsService implements UserDetailsService {

    private final static Logger LOG = LoggerFactory.getLogger(DaoUserDetailsService.class);

    private final AppUserRepository appUserRepository;

    @Autowired
    public DaoUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username)
            .map(appUser -> {
                LOG.debug("User exist");
                return createSpringSecurityUser(appUser);
            })
            .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found."));
    }

    private CustomUserDetails createSpringSecurityUser(AppUser appUser) {
        String username = appUser.getUsername();
        LOG.debug("Username: " + username);
        String password = appUser.getPassword();
        LOG.debug("Password: " + password);

        CustomUserDetailsImpl user = new CustomUserDetailsImpl(
            username,
            password,
            Collections.emptyList()
        );
        user.setUserType(UserType.DAO);

        LOG.debug("UserType: " + user.getUserType());

        return user;
    }
}
