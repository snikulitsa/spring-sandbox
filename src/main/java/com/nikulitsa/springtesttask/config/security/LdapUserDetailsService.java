package com.nikulitsa.springtesttask.config.security;

import com.nikulitsa.springtesttask.services.ldap.LdapMapperFabric;
import com.nikulitsa.springtesttask.services.ldap.LdapQueryFabric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component("ldapUserDetailsService")
public class LdapUserDetailsService implements UserDetailsContextMapper {

    private static final Logger LOG = LoggerFactory.getLogger(LdapUserDetailsService.class);

    private final LdapTemplate ldapTemplate;
    private final LdapQueryFabric ldapQueryFabric;
    private final LdapMapperFabric ldapMapperFabric;

    public LdapUserDetailsService(LdapTemplate ldapTemplate,
                                  LdapQueryFabric ldapQueryFabric,
                                  LdapMapperFabric ldapMapperFabric) {
        this.ldapTemplate = ldapTemplate;
        this.ldapQueryFabric = ldapQueryFabric;
        this.ldapMapperFabric = ldapMapperFabric;
    }

    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx,
                                          String username,
                                          Collection<? extends GrantedAuthority> authorities) {

        Object userPassword = ctx.getObjectAttribute("userPassword");
        String passwd = userPassword != null
            ? (String) userPassword
            : "";

        GrantedAuthority mockRole = new SimpleGrantedAuthority("MOCK_ROLE");
        List<GrantedAuthority> mockRoles = new ArrayList<>(1);
        mockRoles.add(mockRole);

        List<? extends GrantedAuthority> groupCnAuthorities = ldapTemplate.search(
            ldapQueryFabric.groupsByMember(username),
            ldapMapperFabric.groupCnMapper()
        );

        mockRoles.addAll(groupCnAuthorities);

        //TODO this is mock
        User user = new User(username, passwd, mockRoles);
        String msg = "\n=====================\n"
            + "Username: " + user.getUsername() + "\n"
            + "Password: " + user.getPassword() + "\n"
            + "Roles: " + user.getAuthorities()
            + "\n=====================";
        LOG.debug(msg);
        return user;
    }

    @Override
    public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
        throw new UnsupportedOperationException(
            this.getClass().getSimpleName() + " does not support 'mapUserToContext' operation."
        );
    }
}
