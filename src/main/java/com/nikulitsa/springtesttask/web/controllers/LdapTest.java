package com.nikulitsa.springtesttask.web.controllers;

import com.nikulitsa.springtesttask.services.ldap.LdapService;
import com.nikulitsa.springtesttask.web.dto.ldap.LdapTreeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ldap_test")
public class LdapTest {

    private final LdapService ldapService;

    @Autowired
    public LdapTest(LdapService ldapService) {
        this.ldapService = ldapService;
    }

    @GetMapping
    public String getAllUsers() {
        return ldapService.getAllUsers();
    }

    @GetMapping("/mapDnFromUsername")
    public String getDnByUsername(@RequestBody String username) {
        return ldapService.getDnByUsername(username);
    }

    @PostMapping("/getByDnBase")
    public LdapTreeResponse getByDnBase(@RequestBody(required = false) String baseDn) {
        return ldapService.getByDnBase(baseDn);
    }

    @PostMapping("/groupMembers")
    public List<String> getGroupMembers(@RequestBody(required = false) String groupDn) {
        return ldapService.getGroupMembers(groupDn);
    }
}
