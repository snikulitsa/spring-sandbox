package com.nikulitsa.springsandbox.repositories;

import com.nikulitsa.springsandbox.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий сущности {@link AppUser}.
 *
 * @author Sergey Nikulitsa
 */
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByMail(String mail);
}
