package com.nikulitsa.springsandbox.web.controllers;

import com.nikulitsa.springsandbox.services.RegistrationService;
import com.nikulitsa.springsandbox.web.dto.RegistrationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер регистрации пользователя по email.
 *
 * @author Sergey Nikulitsa
 */
@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationService.class);

    private final RegistrationService regService;

    @Autowired
    public RegistrationController(RegistrationService regService) {
        this.regService = regService;
    }

    @PostMapping("/mail")
    public void registerByEmail(@RequestBody RegistrationRequest regRequest) {
        String mail = regRequest.getEmail();
        LOG.debug("Registration user with e-mail: " + mail);
        regService.register(mail);
    }
}
