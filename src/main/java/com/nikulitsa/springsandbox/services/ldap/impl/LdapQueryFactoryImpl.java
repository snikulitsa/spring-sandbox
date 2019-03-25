package com.nikulitsa.springsandbox.services.ldap.impl;

import com.nikulitsa.springsandbox.entities.ldap.LdapFields;
import com.nikulitsa.springsandbox.entities.ldap.LdapObjectClass;
import com.nikulitsa.springsandbox.services.ldap.LdapQueryFactory;
import com.nikulitsa.springsandbox.utils.LdapExtendedUtils;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.filter.HardcodedFilter;
import org.springframework.ldap.query.ContainerCriteria;
import org.springframework.ldap.query.SearchScope;
import org.springframework.stereotype.Component;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

/**
 * @author Sergey Nikulitsa
 * @see LdapQueryFactory
 */
@Component
public class LdapQueryFactoryImpl implements LdapQueryFactory {

    @Override
    public ContainerCriteria ldapEntityQuery(String baseDn, LdapObjectClass ldapObjectClass) {
        return query()
            .base(baseDn)
            .searchScope(SearchScope.ONELEVEL)
            .where(LdapFields.OBJECT_CLASS).is(ldapObjectClass.getValue());
    }

    @Override
    public Filter ldapEntityByBinaryObjectGUIDRawQuery(byte[] objectGUID) {
        return new HardcodedFilter(
            "(objectGUID=" +
                LdapExtendedUtils.objectGUIDToString(objectGUID) +
                ")"
        );
    }

    @Override
    public ContainerCriteria ldapOneTreeEntityQuery(LdapObjectClass ldapObjectClass, String entityDn) {
        return query()
            .where(LdapFields.OBJECT_CLASS).is(ldapObjectClass.getValue())
            .and(LdapFields.DISTINGUISHED_NAME).is(entityDn);
    }

    @Override
    public ContainerCriteria byDnQuery(String dn) {
        return whereAttributeIs(LdapFields.DISTINGUISHED_NAME, dn);
    }

    @Override
    public ContainerCriteria dnByUsername(String username) {
        return whereAttributeIs(LdapFields.S_AMA_ACCOUNT_NAME, username);
    }

    @Override
    public ContainerCriteria dnByUserPrincipalName(String userPrincipalName) {
        return whereAttributeIs(LdapFields.USER_PRINCIPAL_NAME, userPrincipalName);
    }

    @Override
    public ContainerCriteria groupsByMember(String memberDn) {
        return whereAttributeIs(LdapFields.MEMBER, memberDn);
    }

    @Override
    public ContainerCriteria objectByDn(String objectDn) {
        return whereAttributeIs(LdapFields.DISTINGUISHED_NAME, objectDn);
    }

    @Override
    public ContainerCriteria whereAttributeIs(String attribute, String value) {
        return query().where(attribute).is(value);
    }
}
