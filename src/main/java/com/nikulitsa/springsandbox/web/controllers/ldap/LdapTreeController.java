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

    @GetMapping
    public String getAllUsers() {
        return ldapTreeService.getAllUsers();
    }

    @GetMapping("/branch")
    public LdapTreeEntityResponse getLdapTreeEntityResponse(
        @RequestParam(required = false, defaultValue = "") String baseDn) {
        return ldapTreeService.getLdapTreeEntityResponse(baseDn);
    }

    @PostMapping("/dnByObjectGUID")
    public String getLdapEntityByObjectGUID(@RequestBody LdapEntityByObjectGUIDRequest request) {
        return ldapTreeService.getLdapEntityByObjectGUID(request);
    }

    //TODO move to LdapUserController
    @GetMapping("/mapDnFromUsername")
    public String getDnByUsername(@RequestParam String username) {
        return ldapTreeService.getUserDnByUsername(username);
    }

    //TODO move to LdapGroupController
    //    @PostMapping("/groupMembers")
    //    public List<String> getGroupMembers(@RequestBody LdapTreeRequest request) {
    //        return ldapTreeService.getGroupMembers(request);
    //    }
}
