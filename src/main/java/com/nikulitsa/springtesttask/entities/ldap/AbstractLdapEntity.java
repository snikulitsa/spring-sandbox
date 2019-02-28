package com.nikulitsa.springtesttask.entities.ldap;

public abstract class AbstractLdapEntity {

    private String dn;

    private byte[] objectGuid;

    private LdapObjectClass ldapObjectClass;

    public AbstractLdapEntity(String dn, byte[] objectGuid) {
        this.dn = dn;
        this.objectGuid = objectGuid;
    }

    public String getDn() {
        return dn;
    }

    public AbstractLdapEntity setDn(String dn) {
        this.dn = dn;
        return this;
    }

    public byte[] getObjectGuid() {
        return objectGuid;
    }

    public AbstractLdapEntity setObjectGuid(byte[] objectGuid) {
        this.objectGuid = objectGuid;
        return this;
    }

    public LdapObjectClass getLdapObjectClass() {
        return ldapObjectClass;
    }

    public AbstractLdapEntity setLdapObjectClass(LdapObjectClass ldapObjectClass) {
        this.ldapObjectClass = ldapObjectClass;
        return this;
    }
}
