package com.nikulitsa.springtesttask.entities.ldap;

public class BuiltinDomain extends AbstractLdapEntity {

    private final LdapObjectClass ldapObjectClass = LdapObjectClass.BUILTIN_DOMAIN;

    public BuiltinDomain(String dn, byte[] objectGuid) {
        super(dn, objectGuid);
    }

    @Override
    public LdapObjectClass getLdapObjectClass() {
        return ldapObjectClass;
    }
}
