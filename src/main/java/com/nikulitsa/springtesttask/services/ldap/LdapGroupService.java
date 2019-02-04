package com.nikulitsa.springtesttask.services.ldap;

import com.nikulitsa.springtesttask.config.activedirectory.ActiveDirectoryConfig;
import com.nikulitsa.springtesttask.entities.ldap.LdapGroupFull;
import com.nikulitsa.springtesttask.web.dto.ldap.LdapEntityByObjectGUIDRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import javax.naming.directory.SearchControls;
import java.util.List;

@Service
public class LdapGroupService {

    private final LdapQueryFabric ldapQueryFabric;
    private final LdapTemplate ldapTemplate;

    @Autowired
    public LdapGroupService(LdapQueryFabric ldapQueryFabric,
                            @Qualifier(ActiveDirectoryConfig.LDAP_TEMPLATE_PREFIX + 1) LdapTemplate ldapTemplate) {
        this.ldapQueryFabric = ldapQueryFabric;
        this.ldapTemplate = ldapTemplate;
    }

    public LdapGroupFull getLdapGroupDetailsByObjectGUID(LdapEntityByObjectGUIDRequest request) {

        byte[] objectGUID = request.getObjectGUID();
        List<LdapGroupFull> ldapGroupFulls = ldapTemplate.find(
            LdapSearchUtils.EMPTY_BASE_NAME,
            ldapQueryFabric.ldapEntityByBinaryObjectGUIDRawQuery(objectGUID),
            searchControls(),
            LdapGroupFull.class
        );

        return ldapGroupFulls.stream()
            .findFirst()
            .orElseThrow(
                LdapSearchUtils.entityNotFoundExceptionSupplier(objectGUID)
            );
    }

    private SearchControls searchControls() {
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        return searchControls;
    }
}
