package com.nikulitsa.springtesttask.entities.ldap;

public class OrganizationalUnit extends AbstractLdapEntity {

    private final LdapObjectClass ldapObjectClass = LdapObjectClass.ORGANIZATIONAL_UNIT;

    public OrganizationalUnit(String dn, byte[] objectGuid) {
        super(dn, objectGuid);
    }

    @Override
    public LdapObjectClass getLdapObjectClass() {
        return ldapObjectClass;
    }
}
