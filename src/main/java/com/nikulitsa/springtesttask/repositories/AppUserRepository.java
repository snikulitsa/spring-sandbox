package com.nikulitsa.springtesttask.repositories;

import com.nikulitsa.springtesttask.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByMail(String mail);
}
