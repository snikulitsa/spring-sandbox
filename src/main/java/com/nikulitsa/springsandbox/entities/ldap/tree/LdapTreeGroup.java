package com.nikulitsa.springsandbox.entities.ldap.tree;

import com.nikulitsa.springsandbox.entities.ldap.LdapObjectClass;

/**
 * Легковесная сущность LDAP группы.
 *
 * @author Sergey Nikulitsa
 */
public class LdapTreeGroup extends AbstractLdapTreeEntity {

    /**
     * Тип сущности.
     */
    private final LdapObjectClass ldapObjectClass = LdapObjectClass.GROUP;

    public LdapTreeGroup(String name, String dn, byte[] objectGuid) {
        super(name, dn, objectGuid);
    }

    @Override
    public LdapObjectClass getLdapObjectClass() {
        return ldapObjectClass;
    }
}
