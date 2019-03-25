package com.nikulitsa.springsandbox.entities.ldap.tree;

import com.nikulitsa.springsandbox.entities.ldap.LdapObjectClass;

/**
 * Легковесная сущность LDAP контейнера.
 *
 * @author Sergey Nikulitsa
 */
public class LdapTreeContainer extends AbstractLdapTreeEntity {

    /**
     * Тип сущности.
     */
    private final LdapObjectClass ldapObjectClass = LdapObjectClass.CONTAINER;

    public LdapTreeContainer(String name, String dn, byte[] objectGuid) {
        super(name, dn, objectGuid);
    }

    @Override
    public LdapObjectClass getLdapObjectClass() {
        return ldapObjectClass;
    }
}
