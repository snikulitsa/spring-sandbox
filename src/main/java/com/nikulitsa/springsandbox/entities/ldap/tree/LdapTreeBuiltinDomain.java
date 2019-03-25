package com.nikulitsa.springsandbox.entities.ldap.tree;

import com.nikulitsa.springsandbox.entities.ldap.LdapObjectClass;

/**
 * Легковесная сущность LDAP контейнера по умолчанию.
 *
 * @author Sergey Nikulitsa
 */
public class LdapTreeBuiltinDomain extends AbstractLdapTreeEntity {

    /**
     * Тип сущности.
     */
    private final LdapObjectClass ldapObjectClass = LdapObjectClass.BUILTIN_DOMAIN;

    public LdapTreeBuiltinDomain(String name, String dn, byte[] objectGuid) {
        super(name, dn, objectGuid);
    }

    @Override
    public LdapObjectClass getLdapObjectClass() {
        return ldapObjectClass;
    }
}
