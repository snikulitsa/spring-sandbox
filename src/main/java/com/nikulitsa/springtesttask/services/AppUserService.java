package com.nikulitsa.springtesttask.services;

import com.nikulitsa.springtesttask.entities.AppUser;

import java.util.Optional;

public interface AppUserService {

    Optional<AppUser> getCurrentUser();
}
