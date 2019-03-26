package com.nikulitsa.springsandbox.services.impl;

import com.nikulitsa.springsandbox.entities.AppUser;
import com.nikulitsa.springsandbox.repositories.AppUserRepository;
import com.nikulitsa.springsandbox.services.MailService;
import com.nikulitsa.springsandbox.services.RegistrationService;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.Optional;

/**
 * @author Sergey Nikulitsa
 * @see RegistrationService
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationServiceImpl.class);

    private final MailService mailService;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationServiceImpl(MailService mailService,
                                   AppUserRepository appUserRepository,
                                   PasswordEncoder passwordEncoder) {
        this.mailService = mailService;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void register(String mail) {

        Optional<AppUser> userMonad = appUserRepository.findByUsername(mail);
        if (userMonad.isPresent()) {
            throw new EntityExistsException("User with mail: " + mail + " already exist.");
        }

        String password = RandomStringUtils.randomAscii(12);
        String passwordHash = passwordEncoder.encode(password);

        AppUser appUser = new AppUser()
            .setUsername(mail)
            .setMail(mail)
            .setPassword(passwordHash);

        LOG.debug("Trying to save user: " + appUser.toString());
        appUser = appUserRepository.save(appUser);
        LOG.debug("Saved User: " + appUser);

        mailService.send(mailService.buildMessage(
            mailService.from(),
            "Registration test",
            "Registration success. Your password is: " + password,
            mail
        ));
    }
}
