package com.nikulitsa.springtesttask.config.activedirectory;

import com.nikulitsa.springtesttask.config.userdetails.CustomUserDetails;
import com.nikulitsa.springtesttask.config.userdetails.CustomUserDetailsImpl;
import com.nikulitsa.springtesttask.config.userdetails.UserType;
import com.nikulitsa.springtesttask.services.ldap.LdapMapperFabric;
import com.nikulitsa.springtesttask.services.ldap.LdapQueryFabric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class LdapUserDetailsContextMapper implements UserDetailsContextMapper {

    private static final Logger LOG = LoggerFactory.getLogger(LdapUserDetailsContextMapper.class);

    private final LdapTemplate ldapTemplate;
    private final LdapQueryFabric ldapQueryFabric;
    private final LdapMapperFabric ldapMapperFabric;

    public LdapUserDetailsContextMapper(LdapTemplate ldapTemplate,
                                        LdapQueryFabric ldapQueryFabric,
                                        LdapMapperFabric ldapMapperFabric) {
        this.ldapTemplate = ldapTemplate;
        this.ldapQueryFabric = ldapQueryFabric;
        this.ldapMapperFabric = ldapMapperFabric;
    }

    @Override
    public CustomUserDetails mapUserFromContext(DirContextOperations ctx,
                                                String username,
                                                Collection<? extends GrantedAuthority> authorities) {

        List<GrantedAuthority> roles = new LinkedList<>();

        List<? extends GrantedAuthority> groupCnAuthorities = ldapTemplate.search(
            ldapQueryFabric.groupsByMember(username),
            ldapMapperFabric.groupCnMapper()
        );

        roles.addAll(groupCnAuthorities);
        roles.addAll(authorities);

        CustomUserDetailsImpl user = new CustomUserDetailsImpl(username, "", roles);
        setType(user);
        log(user);
        return user;
    }

    @Override
    public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
        throw new UnsupportedOperationException(
            this.getClass().getSimpleName() + " does not support 'mapUserToContext' operation."
        );
    }

    protected void setType(CustomUserDetails user) {
        user.setUserType(UserType.LDAP);
    }

    protected void log(CustomUserDetails user) {
        String msg = getMsg(user);
        LOG.debug(msg);
    }

    protected String getMsg(CustomUserDetails user) {
        return "\n==================================================================================\n"
            + "Username: " + user.getUsername() + "\n"
            + "Roles: " + user.getAuthorities() + "\n"
            + "UserType: " + user.getUserType()
            + "\n==================================================================================";
    }
}
