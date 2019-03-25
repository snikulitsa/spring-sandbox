package com.nikulitsa.springsandbox.config.activedirectory;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Sergey Nikulitsa
 * @see LdapAuthoritiesPopulator
 */
@Component("ldapAuthoritiesPopulator")
public class LdapAuthoritiesPopulatorImpl implements LdapAuthoritiesPopulator {

    @Override
    public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData,
                                                                        String username) {

        //TODO this is mock
        return Collections.emptyList();
    }
}
