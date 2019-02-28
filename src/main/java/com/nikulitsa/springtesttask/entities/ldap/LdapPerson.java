package com.nikulitsa.springtesttask.entities.ldap;

public class LdapPerson extends AbstractLdapEntity {

    private final LdapObjectClass ldapObjectClass = LdapObjectClass.PERSON;

    public LdapPerson(String dn, byte[] objectGuid) {
        super(dn, objectGuid);
    }

    @Override
    public LdapObjectClass getLdapObjectClass() {
        return ldapObjectClass;
    }
}
