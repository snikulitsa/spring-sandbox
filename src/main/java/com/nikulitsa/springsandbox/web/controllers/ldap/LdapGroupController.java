package com.nikulitsa.springsandbox.web.controllers.ldap;

import com.nikulitsa.springsandbox.entities.ldap.objects.LdapGroup;
import com.nikulitsa.springsandbox.services.ldap.LdapGroupService;
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
 * Контроллер LDAP-групп.
 *
 * @author Sergey Nikulitsa
 */
@RestController
@RequestMapping("/api/ldap/group")
public class LdapGroupController {

    private final LdapGroupService ldapGroupService;

    @Autowired
    public LdapGroupController(LdapGroupService ldapGroupService) {
        this.ldapGroupService = ldapGroupService;
    }

    @PostMapping("/byObjectGUID")
    public LdapGroup getLdapGroupDetailsByObjectGUID(@RequestBody LdapEntityByObjectGUIDRequest request) {
        return ldapGroupService.getByObjectGUID(request);
    }

    @GetMapping("/members")
    public LdapTreeEntityResponse getGroupMembers(@RequestParam String dn) {
        return ldapGroupService.getGroupMembers(dn);
    }
}
