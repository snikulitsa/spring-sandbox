package com.nikulitsa.springtesttask.config.activedirectory;

import com.nikulitsa.springtesttask.config.userdetails.CustomUserDetails;
import com.nikulitsa.springtesttask.config.userdetails.UserType;
import com.nikulitsa.springtesttask.services.ldap.LdapMapperFabric;
import com.nikulitsa.springtesttask.services.ldap.LdapQueryFabric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class KerberosUserDetailsContextMapper extends LdapUserDetailsContextMapper {

    private final static Logger LOG = LoggerFactory.getLogger(KerberosUserDetailsContextMapper.class);

    public KerberosUserDetailsContextMapper(LdapTemplate ldapTemplate,
                                            LdapQueryFabric ldapQueryFabric,
                                            LdapMapperFabric ldapMapperFabric) {
        super(ldapTemplate, ldapQueryFabric, ldapMapperFabric);
    }

    @Override
    protected String prepareUsername(String username) {
        List<String> search = getLdapTemplate().search(
            getLdapQueryFabric().dnByUserPrincipalName(username),
            getLdapMapperFabric().dnMapper()
        );
        return search.stream()
            .findFirst()
            .orElseThrow(() -> new UsernameNotFoundException(
                "User with userPrincipalName '" + username + "' not found in LDAP."
            ));
    }

    @Override
    protected void setType(CustomUserDetails user) {
        user.setUserType(UserType.KERBEROS);
    }

    @Override
    protected void log(CustomUserDetails user) {
        String msg = getMsg(user);
        LOG.debug(msg);
    }
}
