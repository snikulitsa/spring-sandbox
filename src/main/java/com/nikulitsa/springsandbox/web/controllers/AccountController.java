package com.nikulitsa.springsandbox.web.controllers;

import com.nikulitsa.springsandbox.config.userdetails.UserType;
import com.nikulitsa.springsandbox.services.AppUserService;
import com.nikulitsa.springsandbox.web.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер {@link UserType#DAO} аккаунта.
 *
 * @author Sergey Nikulitsa
 */
@RestController
@RequestMapping("/api/account")
public class AccountController {

    private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);

    private final AppUserService userService;

    @Autowired
    public AccountController(AppUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UserDTO getAccount() {
        LOG.debug("Getting Session...");
        return userService.getCurrentUser().map(UserDTO::new)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
