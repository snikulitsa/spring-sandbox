package com.nikulitsa.springsandbox.entities.ldap.tree;

import com.nikulitsa.springsandbox.entities.ldap.LdapObjectClass;

/**
 * Легковесная сущность LDAP пользователя.
 *
 * @author Sergey Nikulitsa
 */
public class LdapTreePerson extends AbstractLdapTreeEntity {

    /**
     * Тип сущности.
     */
    private final LdapObjectClass ldapObjectClass = LdapObjectClass.PERSON;

    public LdapTreePerson(String name, String dn, byte[] objectGuid) {
        super(name, dn, objectGuid);
    }

    @Override
    public LdapObjectClass getLdapObjectClass() {
        return ldapObjectClass;
    }
}
