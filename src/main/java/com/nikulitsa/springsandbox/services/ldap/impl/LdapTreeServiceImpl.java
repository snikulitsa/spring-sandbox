package com.nikulitsa.springsandbox.services.ldap.impl;

import com.nikulitsa.springsandbox.config.activedirectory.properties.ActiveDirectoryProperties;
import com.nikulitsa.springsandbox.entities.ldap.LdapObjectClass;
import com.nikulitsa.springsandbox.entities.ldap.tree.AbstractLdapTreeEntity;
import com.nikulitsa.springsandbox.services.ldap.LdapMapperFactory;
import com.nikulitsa.springsandbox.services.ldap.LdapQueryFactory;
import com.nikulitsa.springsandbox.services.ldap.LdapTreeService;
import com.nikulitsa.springsandbox.utils.LdapExtendedUtils;
import com.nikulitsa.springsandbox.web.dto.ldap.LdapEntityByObjectGUIDRequest;
import com.nikulitsa.springsandbox.web.dto.ldap.LdapTreeEntityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Service;

import javax.naming.ldap.LdapName;
import java.util.List;

import static com.nikulitsa.springsandbox.utils.ExceptionFactory.entityNotFoundExceptionSupplier;

/**
 * @author Sergey Nikulitsa
 * @see LdapTreeService
 */
@Service
public class LdapTreeServiceImpl implements LdapTreeService {

    private final ActiveDirectoryProperties activeDirectoryProperties;
    private final LdapTemplate ldapTemplate;
    private final LdapQueryFactory ldapQueryFactory;
    private final LdapMapperFactory ldapMapperFactory;

    @Autowired
    public LdapTreeServiceImpl(ActiveDirectoryProperties activeDirectoryProperties,
                               LdapTemplate ldapTemplate,
                               LdapQueryFactory ldapQueryFactory,
                               LdapMapperFactory ldapMapperFactory) {
        this.activeDirectoryProperties = activeDirectoryProperties;
        this.ldapTemplate = ldapTemplate;
        this.ldapQueryFactory = ldapQueryFactory;
        this.ldapMapperFactory = ldapMapperFactory;
    }

    public String getDnByObjectGUID(LdapEntityByObjectGUIDRequest request) {
        byte[] objectGUID = request.getObjectGUID();
        String filter = ldapQueryFactory.ldapEntityByBinaryObjectGUIDRawQuery(objectGUID).encode();
        List<String> search = ldapTemplate.search(
            LdapExtendedUtils.EMPTY_BASE_STRING,
            filter,
            ldapMapperFactory.dnMapper()
        );

        return search.stream()
            .findFirst()
            .orElseThrow(entityNotFoundExceptionSupplier(AbstractLdapTreeEntity.class, objectGUID));
    }

    public LdapTreeEntityResponse getLdapTreeEntityResponse(String baseDn) {
        baseDn = prepareBase(baseDn);
        return new LdapTreeEntityResponse()
            .setLdapGroups(search(LdapObjectClass.GROUP, baseDn))
            .setLdapPersons(search(LdapObjectClass.PERSON, baseDn))
            .setLdapContainers(search(LdapObjectClass.CONTAINER, baseDn))
            .setBuiltinDomains(search(LdapObjectClass.BUILTIN_DOMAIN, baseDn))
            .setOrganizationalUnits(search(LdapObjectClass.ORGANIZATIONAL_UNIT, baseDn));
    }

    private List<AbstractLdapTreeEntity> search(LdapObjectClass ldapObjectClass, String dn) {
        return ldapTemplate.search(
            ldapQueryFactory.ldapEntityQuery(dn, ldapObjectClass),
            ldapMapperFactory.ldapTreeEntityMapper(ldapObjectClass)
        );
    }

    private String prepareBase(String dn) {
        LdapName ldapName = LdapUtils.newLdapName(dn);
        LdapName base = LdapUtils.newLdapName(activeDirectoryProperties.getBase());
        LdapName result = LdapUtils.removeFirst(ldapName, base);
        return result.toString();
    }
}
