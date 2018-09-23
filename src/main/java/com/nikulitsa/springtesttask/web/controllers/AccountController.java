package com.nikulitsa.springtesttask.web.controllers;

import com.nikulitsa.springtesttask.repositories.AppUserRepository;
import com.nikulitsa.springtesttask.services.AppUserService;
import com.nikulitsa.springtesttask.web.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final static Logger log = LoggerFactory.getLogger(AccountController.class);

    private final AppUserService userService;

    @Autowired
    public AccountController(AppUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/account")
    public UserDTO getAccount() {
        log.debug("Getting Session...");
        return userService.getCurrentUser().map(UserDTO::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
