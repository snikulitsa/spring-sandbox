package com.nikulitsa.springtesttask.services.ldap;

import com.nikulitsa.springtesttask.config.ldap.properties.AbstractLdapProperties;
import org.springframework.ldap.query.ContainerCriteria;
import org.springframework.ldap.query.SearchScope;
import org.springframework.stereotype.Component;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Component
public class LdapQueryFabric {

    private static final String OBJECT_CATEGORY = "objectCategory";
    private static final String OBJECT_CLASS = "objectClass";
    private static final String DISTINGUISHED_NAME = "distinguishedName";
    private static final String GROUP = "group";
    private static final String CONTAINER = "container";

    public ContainerCriteria personsQuery(String baseDn, AbstractLdapProperties ldapProperties) {
        return query()
            .base(baseDn)
            .searchScope(SearchScope.ONELEVEL)
            .where(OBJECT_CATEGORY)
            .is(ldapProperties.getAttributes().getPerson_object_category());
    }

    public ContainerCriteria organizationalUnitsQuery(String baseDn, AbstractLdapProperties ldapProperties) {
        return query()
            .base(baseDn)
            .searchScope(SearchScope.ONELEVEL)
            .where(OBJECT_CLASS)
            .is(ldapProperties.getAttributes().getOrganizational_unit_object_class());
    }

    public ContainerCriteria groupsQuery(String baseDn) {
        return query()
            .base(baseDn)
            .searchScope(SearchScope.ONELEVEL)
            .where(OBJECT_CATEGORY).is(GROUP);
    }

    public ContainerCriteria containersQuery(String baseDn) {
        return query()
            .base(baseDn)
            .searchScope(SearchScope.ONELEVEL)
            .where(OBJECT_CLASS).is(CONTAINER);
    }

    public ContainerCriteria dnByUsername(String username, AbstractLdapProperties ldapProperties) {
        return whereAttributeIs(ldapProperties.getAttributes().getUsername(), username);
    }

    public ContainerCriteria groupsByMember(String memberDn) {
        return whereAttributeIs("member", memberDn);
    }

    public ContainerCriteria objectByDn(String objectDn) {
        return whereAttributeIs(DISTINGUISHED_NAME, objectDn);
    }

    public ContainerCriteria whereAttributeIs(String attribute, String value) {
        return query().where(attribute).is(value);
    }
}
