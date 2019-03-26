package com.nikulitsa.springsandbox.entities.ldap.tree;

import com.nikulitsa.springsandbox.entities.ldap.LdapObjectClass;

/**
 * Легковесная сущность LDAP организационной единицы.
 *
 * @author Sergey Nikulitsa
 */
public class LdapTreeOrganizationalUnit extends AbstractLdapTreeEntity {

    /**
     * Тип сущности.
     */
    private final LdapObjectClass ldapObjectClass = LdapObjectClass.ORGANIZATIONAL_UNIT;

    public LdapTreeOrganizationalUnit(String name, String dn, byte[] objectGuid) {
        super(name, dn, objectGuid);
    }

    @Override
    public LdapObjectClass getLdapObjectClass() {
        return ldapObjectClass;
    }
}
