package com.nikulitsa.springtesttask.repositories;

import com.nikulitsa.springtesttask.entities.AppUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppAppUserRepositoryTest {

    private final static Logger log = LoggerFactory.getLogger(AppAppUserRepositoryTest.class);

    @Autowired
    private AppUserRepository appUserRepository;

    @Test
    public void findByUsernameTest() {
        log.debug("trying to find user");

        Optional<AppUser> userMonad = appUserRepository.findByUsername("sa.nikulitsa@gmail.com");
        if (userMonad.isPresent()) {
            log.debug(userMonad.get().toString());
        } else {
            log.debug("USER NOT FOUND");
        }

        log.debug("user finding finish");
    }
}
