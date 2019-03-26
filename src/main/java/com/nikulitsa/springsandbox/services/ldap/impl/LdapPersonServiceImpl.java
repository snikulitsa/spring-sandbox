package com.nikulitsa.springsandbox.services.ldap.impl;

import com.nikulitsa.springsandbox.entities.ldap.objects.LdapPerson;
import com.nikulitsa.springsandbox.services.ldap.LdapMapperFactory;
import com.nikulitsa.springsandbox.services.ldap.LdapPersonService;
import com.nikulitsa.springsandbox.services.ldap.LdapQueryFactory;
import com.nikulitsa.springsandbox.web.dto.ldap.LdapEntityByObjectGUIDRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.nikulitsa.springsandbox.utils.ExceptionFactory.entityNotFoundExceptionSupplier;
import static com.nikulitsa.springsandbox.utils.ExceptionFactory.usernameNotFoundExceptionSupplier;

/**
 * @author Sergey Nikulitsa
 * @see LdapPersonService
 */
@Service
public class LdapPersonServiceImpl extends AbstractLdapEntityService<LdapPerson> implements LdapPersonService {

    @Autowired
    public LdapPersonServiceImpl(LdapQueryFactory ldapQueryFactory,
                                 LdapMapperFactory ldapMapperFactory,
                                 LdapTemplate ldapTemplate) {
        super(ldapQueryFactory, ldapMapperFactory, ldapTemplate);
    }

    @Override
    public String getAllUsers() {

        List<String> search = ldapTemplate().search(
            "",
            "(objectCategory=person)",
            ldapMapperFactory().debugMapper()
        );

        return String.join(
            "<li>====================================================================================</li>\n"
                + "<li>====================================================================================</li>\n",
            search
        );
    }

    @Override
    public String getUserDnByUsername(String username) {
        List<String> search = ldapTemplate().search(
            ldapQueryFactory().dnByUsername(username),
            ldapMapperFactory().dnMapper()
        );
        return search.stream()
            .findFirst()
            .orElseThrow(usernameNotFoundExceptionSupplier(username));
    }

    @Override
    public LdapPerson getByObjectGUID(LdapEntityByObjectGUIDRequest request) {
        return getEntityByObjectGUID(request, LdapPerson.class)
            .orElseThrow(entityNotFoundExceptionSupplier(LdapPerson.class, request.getObjectGUID()));
    }

    @Override
    public LdapPerson getByDN(String personDN) {
        return getEntityByDN(personDN, LdapPerson.class)
            .orElseThrow(entityNotFoundExceptionSupplier(LdapPerson.class, personDN));
    }
}
