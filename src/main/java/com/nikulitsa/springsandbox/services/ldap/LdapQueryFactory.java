package com.nikulitsa.springsandbox.services.ldap;

import com.nikulitsa.springsandbox.entities.ldap.LdapObjectClass;
import com.nikulitsa.springsandbox.entities.ldap.tree.AbstractLdapTreeEntity;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.query.ContainerCriteria;

/**
 * @author Sergey Nikulitsa
 */
public interface LdapQueryFactory {
    ContainerCriteria ldapEntityQuery(String baseDn, LdapObjectClass ldapObjectClass);

    Filter ldapEntityByBinaryObjectGUIDRawQuery(byte[] objectGUID);

    /**
     * Получить {@link AbstractLdapTreeEntity} по её Distinguished Name.
     *
     * @param ldapObjectClass {@link LdapObjectClass}
     * @param entityDn        Distinguished Name искомого объекта
     * @return {@link ContainerCriteria}
     */
    ContainerCriteria ldapOneTreeEntityQuery(LdapObjectClass ldapObjectClass, String entityDn);

    /**
     * Получение LDAP объекта по Distinguished Name.
     *
     * @param dn Distinguished Name
     * @return {@link ContainerCriteria}
     */
    ContainerCriteria byDnQuery(String dn);

    ContainerCriteria dnByUsername(String username);

    ContainerCriteria dnByUserPrincipalName(String userPrincipalName);

    ContainerCriteria groupsByMember(String memberDn);

    ContainerCriteria objectByDn(String objectDn);

    ContainerCriteria whereAttributeIs(String attribute, String value);
}
