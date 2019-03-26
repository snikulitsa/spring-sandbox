package com.nikulitsa.springsandbox.config.activedirectory;

import com.nikulitsa.springsandbox.config.userdetails.CustomUserDetails;
import com.nikulitsa.springsandbox.config.userdetails.CustomUserDetailsImpl;
import com.nikulitsa.springsandbox.config.userdetails.UserType;
import com.nikulitsa.springsandbox.services.ldap.LdapMapperFactory;
import com.nikulitsa.springsandbox.services.ldap.LdapQueryFactory;
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

/**
 * @author Sergey Nikulitsa
 * @see UserDetailsContextMapper
 */
public class LdapUserDetailsContextMapper implements UserDetailsContextMapper {

    private static final Logger LOG = LoggerFactory.getLogger(LdapUserDetailsContextMapper.class);

    private final LdapTemplate ldapTemplate;
    private final LdapQueryFactory ldapQueryFactory;
    private final LdapMapperFactory ldapMapperFactory;

    public LdapUserDetailsContextMapper(LdapTemplate ldapTemplate,
                                        LdapQueryFactory ldapQueryFactory,
                                        LdapMapperFactory ldapMapperFactory) {
        this.ldapTemplate = ldapTemplate;
        this.ldapQueryFactory = ldapQueryFactory;
        this.ldapMapperFactory = ldapMapperFactory;
    }

    @Override
    public CustomUserDetails mapUserFromContext(DirContextOperations ctx,
                                                String username,
                                                Collection<? extends GrantedAuthority> authorities) {

        List<GrantedAuthority> roles = new LinkedList<>();

        List<? extends GrantedAuthority> groupCnAuthorities = ldapTemplate.search(
            ldapQueryFactory.byMemberQuery(username),
            ldapMapperFactory.groupCnMapper()
        );

        roles.addAll(groupCnAuthorities);
        roles.addAll(authorities);

        CustomUserDetailsImpl user = new CustomUserDetailsImpl(username, "", roles);
        user.setUserType(UserType.ACTIVE_DIRECTORY);
        return user;
    }

    @Override
    public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
        throw new UnsupportedOperationException(
            this.getClass().getSimpleName() + " does not support 'mapUserToContext' operation."
        );
    }
}
