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

    ContainerCriteria byUsernameQuery(String username);

    ContainerCriteria byUserPrincipalNameQuery(String userPrincipalName);

    ContainerCriteria byMemberQuery(String memberDn);

    ContainerCriteria whereAttributeIs(String attribute, String value);
}
