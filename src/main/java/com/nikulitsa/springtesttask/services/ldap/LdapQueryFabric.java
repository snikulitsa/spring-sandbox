package com.nikulitsa.springtesttask.services.ldap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.query.ContainerCriteria;
import org.springframework.ldap.query.SearchScope;
import org.springframework.stereotype.Component;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Component
public class LdapQueryFabric {

    private static final String OBJECT_CATEGORY = "objectCategory";
    private static final String OBJECT_CLASS = "objectClass";
    private static final String DISTINGUISHED_NAME = "distinguishedName";

    @Value("${ldap.attributes.person_object_category}")
    private String personsObjectCategory;

    @Value("${ldap.attributes.organizational_unit_object_class}")
    private String organizationalUnitObjectClass;

    public ContainerCriteria personsQuery(String baseDn) {
        return query()
            .base(baseDn)
            .searchScope(SearchScope.ONELEVEL)
            .where(OBJECT_CATEGORY).is(personsObjectCategory);
    }

    public ContainerCriteria organizationalUnitsQuery(String baseDn) {
        return query()
            .base(baseDn)
            .searchScope(SearchScope.ONELEVEL)
            .where(OBJECT_CLASS).is(organizationalUnitObjectClass);
    }

    public ContainerCriteria groupsQuery(String baseDn) {
        return query()
            .base(baseDn)
            .searchScope(SearchScope.ONELEVEL)
            .where(OBJECT_CATEGORY).is("group");
    }

    public ContainerCriteria containersQuery(String baseDn) {
        return query()
            .base(baseDn)
            .searchScope(SearchScope.ONELEVEL)
            .where(OBJECT_CLASS).is("container");
    }

    public ContainerCriteria groupsByMember(String memberDn) {
        return query().where("member").is(memberDn);
    }

    public ContainerCriteria objectByDn(String objectDn) {
        return query().where(DISTINGUISHED_NAME).is(objectDn);
    }
}
