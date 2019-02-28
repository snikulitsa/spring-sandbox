package com.nikulitsa.springtesttask.web.dto.ldap;

public class LdapEntityByObjectGUIDRequest {

    private byte[] objectGUID;

    public byte[] getObjectGUID() {
        return objectGUID;
    }

    public LdapEntityByObjectGUIDRequest setObjectGUID(byte[] objectGUID) {
        this.objectGUID = objectGUID;
        return this;
    }
}
