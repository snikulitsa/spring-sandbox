package com.nikulitsa.springtesttask.services;

import com.nikulitsa.springtesttask.config.security.SecurityUtils;
import com.nikulitsa.springtesttask.entities.AppUser;
import com.nikulitsa.springtesttask.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
