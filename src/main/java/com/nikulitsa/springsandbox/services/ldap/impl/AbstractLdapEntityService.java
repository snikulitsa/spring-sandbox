package com.nikulitsa.springsandbox.services.ldap.impl;

import com.nikulitsa.springsandbox.services.ldap.BaseLdapEntityService;
import com.nikulitsa.springsandbox.services.ldap.LdapMapperFactory;
import com.nikulitsa.springsandbox.services.ldap.LdapQueryFactory;
import com.nikulitsa.springsandbox.utils.LdapExtendedUtils;
import com.nikulitsa.springsandbox.web.dto.ldap.LdapEntityByObjectGUIDRequest;
import org.springframework.ldap.core.LdapTemplate;

import java.util.Optional;

/**
 * @param <T> LDAP объект
 * @author Sergey Nikulitsa
 */
public abstract class AbstractLdapEntityService<T> implements BaseLdapEntityService<T> {

    private final LdapQueryFactory ldapQueryFactory;
    private final LdapMapperFactory ldapMapperFactory;
    private final LdapTemplate ldapTemplate;

    protected AbstractLdapEntityService(LdapQueryFactory ldapQueryFactory,
                                        LdapMapperFactory ldapMapperFactory,
                                        LdapTemplate ldapTemplate) {
        this.ldapQueryFactory = ldapQueryFactory;
        this.ldapMapperFactory = ldapMapperFactory;
        this.ldapTemplate = ldapTemplate;
    }

    @Override
    public Optional<T> getEntityByObjectGUID(LdapEntityByObjectGUIDRequest request, Class<T> clazz) {
        byte[] objectGUID = request.getObjectGUID();
        T entityByObjectGUID = ldapTemplate().searchForObject(
            LdapExtendedUtils.EMPTY_BASE_NAME,
            ldapQueryFactory().ldapEntityByBinaryObjectGUIDRawQuery(objectGUID).encode(),
            ldapMapperFactory().objectDirectoryContextMapper(ldapTemplate(), clazz)
        );
        return Optional.ofNullable(entityByObjectGUID);
    }

    @Override
    public Optional<T> getEntityByDN(String dn, Class<T> clazz) {
        T entityByDN = ldapTemplate().findOne(
            ldapQueryFactory().byDnQuery(dn),
            clazz
        );
        return Optional.ofNullable(entityByDN);
    }

    public LdapQueryFactory ldapQueryFactory() {
        return ldapQueryFactory;
    }

    public LdapMapperFactory ldapMapperFactory() {
        return ldapMapperFactory;
    }

    public LdapTemplate ldapTemplate() {
        return ldapTemplate;
    }
}

