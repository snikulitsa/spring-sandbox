package com.nikulitsa.springtesttask.entities.ldap;

public class LdapContainer extends AbstractLdapEntity {

    private final LdapObjectClass ldapObjectClass = LdapObjectClass.CONTAINER;

    public LdapContainer(String dn, byte[] objectGuid) {
        super(dn, objectGuid);
    }

    @Override
    public LdapObjectClass getLdapObjectClass() {
        return ldapObjectClass;
    }
}
