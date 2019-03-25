package com.nikulitsa.springsandbox.services.impl;

import com.nikulitsa.springsandbox.config.security.SecurityUtils;
import com.nikulitsa.springsandbox.entities.AppUser;
import com.nikulitsa.springsandbox.repositories.AppUserRepository;
import com.nikulitsa.springsandbox.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Sergey Nikulitsa
 */
@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository userRepository;

    @Autowired
    public AppUserServiceImpl(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<AppUser> getCurrentUser() {
        return SecurityUtils.getCurrentUserLogin().flatMap(userRepository::findByUsername);
    }
}
