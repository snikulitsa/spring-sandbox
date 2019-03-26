package com.nikulitsa.springsandbox.web.controllers.ldap;

import com.nikulitsa.springsandbox.entities.ldap.objects.LdapPerson;
import com.nikulitsa.springsandbox.services.ldap.LdapPersonService;
import com.nikulitsa.springsandbox.web.dto.ldap.LdapEntityByObjectGUIDRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер LDAP-пользователей.
 *
 * @author Sergey Nikulitsa
 */
@RestController
@RequestMapping("/api/ldap/person")
public class LdapPersonController {

    private final LdapPersonService service;

    @Autowired
    public LdapPersonController(LdapPersonService service) {
        this.service = service;
    }

    @GetMapping
    public LdapPerson byDN(@RequestParam String dn) {
        return service.getByDN(dn);
    }

    @PostMapping
    public LdapPerson byObjectGUID(@RequestBody LdapEntityByObjectGUIDRequest request) {
        return service.getByObjectGUID(request);
    }

    @GetMapping("/all")
    public String getAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/dnByUsername")
    public String userDnByUsername(@RequestParam String username) {
        return service.getUserDnByUsername(username);
    }
}
