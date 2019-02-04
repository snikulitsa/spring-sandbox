package com.nikulitsa.springtesttask.web.controllers.ldap;

import com.nikulitsa.springtesttask.entities.ldap.LdapGroupFull;
import com.nikulitsa.springtesttask.services.ldap.LdapGroupService;
import com.nikulitsa.springtesttask.web.dto.ldap.LdapEntityByObjectGUIDRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ldap_test/group")
public class LdapGroupFullController {

    private final LdapGroupService ldapGroupService;

    @Autowired
    public LdapGroupFullController(LdapGroupService ldapGroupService) {
        this.ldapGroupService = ldapGroupService;
    }

    @PostMapping
    public LdapGroupFull getLdapGroupDetailsByObjectGUID(@RequestBody LdapEntityByObjectGUIDRequest request) {
        return ldapGroupService.getLdapGroupDetailsByObjectGUID(request);
    }
}
