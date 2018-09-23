package com.nikulitsa.springtesttask.web.controllers;

import com.nikulitsa.springtesttask.services.RegistrationService;
import com.nikulitsa.springtesttask.web.dto.RegistrationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    private final static Logger log = LoggerFactory.getLogger(RegistrationService.class);

    private final RegistrationService regService;

    @Autowired
    public RegistrationController(RegistrationService regService) {
        this.regService = regService;
    }

    @PostMapping("/mail")
    public void registerByEmail(@RequestBody RegistrationRequest regRequest) {
        String mail = regRequest.getEmail();
        log.debug("Registration user with e-mail: " + mail);
        regService.register(mail);
    }
}
