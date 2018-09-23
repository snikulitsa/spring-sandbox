package com.nikulitsa.springtesttask.services;

import com.nikulitsa.springtesttask.entities.AppUser;
import com.nikulitsa.springtesttask.repositories.AppUserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.Optional;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final static Logger log = LoggerFactory.getLogger(RegistrationServiceImpl.class);

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

        log.debug("Trying to save user: " + appUser.toString());
        appUser = appUserRepository.save(appUser);
        log.debug("Saved User: " + appUser);

        //TODO: handle situation, when user saved to database, but problems happen when sending email
        mailService.send(mailService.buildMessage(
                mailService.from(),
                "Registration test",
                "Registration success. Your password is: " + password,
                mail
        ));
    }
}
