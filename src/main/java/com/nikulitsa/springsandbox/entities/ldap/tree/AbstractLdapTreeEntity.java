package com.nikulitsa.springsandbox.entities.ldap.tree;

import com.nikulitsa.springsandbox.entities.ldap.LdapObjectClass;

/**
 * Абстрактный элемент LDAP дерева.
 *
 * @author Sergey Nikulitsa
 */
public abstract class AbstractLdapTreeEntity {

    /**
     * Имя элемента.
     */
    private String name;

    /**
     * Distinguished Name.
     */
    private String dn;

    /**
     * ObjectGUID.
     */
    private byte[] objectGuid;

    public AbstractLdapTreeEntity(String name, String dn, byte[] objectGuid) {
        this.name = name;
        this.dn = dn;
        this.objectGuid = objectGuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDn() {
        return dn;
    }

    public AbstractLdapTreeEntity setDn(String dn) {
        this.dn = dn;
        return this;
    }

    public byte[] getObjectGuid() {
        return objectGuid;
    }

    public AbstractLdapTreeEntity setObjectGuid(byte[] objectGuid) {
        this.objectGuid = objectGuid;
        return this;
    }

    public abstract LdapObjectClass getLdapObjectClass();
}
