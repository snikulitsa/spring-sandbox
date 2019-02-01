package com.nikulitsa.springtesttask.services.ldap;

import com.nikulitsa.springtesttask.config.activedirectory.properties.AbstractActiveDirectoryProperties;
import com.nikulitsa.springtesttask.entities.ldap.LdapFields;
import com.nikulitsa.springtesttask.entities.ldap.LdapObjectClass;
import org.springframework.ldap.query.ContainerCriteria;
import org.springframework.ldap.query.SearchScope;
import org.springframework.stereotype.Component;

import static org.springframework.ldap.query.LdapQueryBuilder.*;

@Component
public class LdapQueryFabric {

    public ContainerCriteria ldapEntityQuery(String baseDn, LdapObjectClass ldapObjectClass) {
        return query()
            .base(baseDn)
            .searchScope(SearchScope.ONELEVEL)
            .where(LdapFields.OBJECT_CLASS).is(ldapObjectClass.getValue());
    }

    public String ldapEntityByObjectGUIDQuery(byte[] objectGUID) {
        String stringObjectGUID = LdapSearchUtils.converObjectGUID(objectGUID);
        return query().where(LdapFields.OBJECT_GUID).is(stringObjectGUID).filter().encode();
    }

    public ContainerCriteria ldapEntityByObjectGUIDQuery(String objectGUID) {
        return query().where(LdapFields.OBJECT_GUID).is(objectGUID);
    }

    public ContainerCriteria personsQuery(String baseDn) {
        return query()
            .base(baseDn)
            .searchScope(SearchScope.ONELEVEL)
            .where(LdapFields.OBJECT_CLASS).is(LdapFields.PERSON);
    }

    public ContainerCriteria organizationalUnitsQuery(String baseDn) {
        return query()
            .base(baseDn)
            .searchScope(SearchScope.ONELEVEL)
            .where(LdapFields.OBJECT_CLASS).is(LdapFields.ORGANIZATIONAL_UNIT);
    }

    public ContainerCriteria groupsQuery(String baseDn) {
        return query()
            .base(baseDn)
            .searchScope(SearchScope.ONELEVEL)
            .where(LdapFields.OBJECT_CLASS).is(LdapFields.GROUP);
    }

    public ContainerCriteria containersQuery(String baseDn) {
        return query()
            .base(baseDn)
            .searchScope(SearchScope.ONELEVEL)
            .where(LdapFields.OBJECT_CLASS).is(LdapFields.CONTAINER);
    }

    public ContainerCriteria dnByUsername(String username,
                                          AbstractActiveDirectoryProperties activeDirectoryProperties) {

        return whereAttributeIs(activeDirectoryProperties.getAttributes().getUsername(), username);
    }

    public ContainerCriteria dnByUserPrincipalName(String userPrincipalName) {
        return whereAttributeIs(LdapFields.USER_PRINCIPAL_NAME, userPrincipalName);
    }

    public ContainerCriteria groupsByMember(String memberDn) {
        return whereAttributeIs(LdapFields.MEMBER, memberDn);
    }

    public ContainerCriteria objectByDn(String objectDn) {
        return whereAttributeIs(LdapFields.DISTINGUISHED_NAME, objectDn);
    }

    public ContainerCriteria whereAttributeIs(String attribute, String value) {
        return query().where(attribute).is(value);
    }
}
