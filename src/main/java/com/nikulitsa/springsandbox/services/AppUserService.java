package com.nikulitsa.springsandbox.services;

import com.nikulitsa.springsandbox.entities.AppUser;

import java.util.Optional;

/**
 * Интерфейс сервиса сущности {@link AppUser}.
 *
 * @author Sergey Nikulitsa
 */
public interface AppUserService {

    Optional<AppUser> getCurrentUser();
}
