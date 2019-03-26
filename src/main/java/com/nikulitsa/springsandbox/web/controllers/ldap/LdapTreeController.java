package com.nikulitsa.springsandbox.web.controllers.ldap;

import com.nikulitsa.springsandbox.services.ldap.LdapTreeService;
import com.nikulitsa.springsandbox.web.dto.ldap.LdapEntityByObjectGUIDRequest;
import com.nikulitsa.springsandbox.web.dto.ldap.LdapTreeEntityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер LDAP-дерева.
 *
 * @author Sergey Nikulitsa
 */
@RestController
@RequestMapping("/api/ldap/tree")
public class LdapTreeController {

    private final LdapTreeService ldapTreeService;

    @Autowired
    public LdapTreeController(LdapTreeService ldapTreeService) {
        this.ldapTreeService = ldapTreeService;
    }

    @GetMapping("/branch")
    public LdapTreeEntityResponse getLdapTreeEntityResponse(
        @RequestParam(required = false, defaultValue = "") String dn) {
        return ldapTreeService.getLdapTreeEntityResponse(dn);
    }

    @PostMapping("/dnByObjectGUID")
    public String dnByObjectGUID(@RequestBody LdapEntityByObjectGUIDRequest request) {
        return ldapTreeService.getDnByObjectGUID(request);
    }
}
