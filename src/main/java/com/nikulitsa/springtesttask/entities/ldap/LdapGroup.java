package com.nikulitsa.springtesttask.entities.ldap;

public class LdapGroup extends AbstractLdapEntity {

    private final LdapObjectClass ldapObjectClass = LdapObjectClass.GROUP;

    public LdapGroup(String dn, byte[] objectGuid) {
        super(dn, objectGuid);
    }

    @Override
    public LdapObjectClass getLdapObjectClass() {
        return ldapObjectClass;
    }
}
