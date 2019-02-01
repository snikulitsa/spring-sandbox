package com.nikulitsa.springtesttask.web.controllers;

import com.nikulitsa.springtesttask.entities.ldap.AbstractLdapEntity;
import com.nikulitsa.springtesttask.services.ldap.LdapService;
import com.nikulitsa.springtesttask.web.dto.ldap.LdapDnByUsernameRequest;
import com.nikulitsa.springtesttask.web.dto.ldap.LdapEntityByObjectGUIDRequest;
import com.nikulitsa.springtesttask.web.dto.ldap.LdapTreeEntityResponse;
import com.nikulitsa.springtesttask.web.dto.ldap.LdapTreeRequest;
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
public class LdapController {

    private final LdapService ldapService;

    @Autowired
    public LdapController(LdapService ldapService) {
        this.ldapService = ldapService;
    }

    @GetMapping
    public String getAllUsers() {
        return ldapService.getAllUsers();
    }

    @GetMapping("/allDomains")
    public List<String> allDomains() {
        return ldapService.getAllDomains();
    }

    @PostMapping("/ldapObject")
    public LdapTreeResponse getLdapObject(@RequestBody LdapTreeRequest request) {
        return ldapService.getLdapObject(request);
    }

    @PostMapping("/ldapTreePart")
    public LdapTreeEntityResponse getLdapTreeEntityResponse(@RequestBody LdapTreeRequest request) {
        return ldapService.getLdapTreeEntityResponse(request);
    }

    @PostMapping("/dnByObjectGUID")
    public String getLdapEntityByObjectGUID(@RequestBody LdapEntityByObjectGUIDRequest request) {
        return ldapService.getLdapEntityByObjectGUID(request);
    }

    @PostMapping("/mapDnFromUsername")
    public String getDnByUsername(@RequestBody LdapDnByUsernameRequest request) {
        return ldapService.getDnByUsername(request);
    }

    @PostMapping("/groupMembers")
    public List<String> getGroupMembers(@RequestBody LdapTreeRequest request) {
        return ldapService.getGroupMembers(request);
    }
}
