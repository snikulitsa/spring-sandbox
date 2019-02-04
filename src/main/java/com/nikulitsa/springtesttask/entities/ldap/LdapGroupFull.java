package com.nikulitsa.springtesttask.entities.ldap;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

@Entry(objectClasses = LdapFields.GROUP)
public class LdapGroupFull {

    @Id
    private Name id;

    @Attribute(name = LdapFields.OBJECT_GUID, type = Attribute.Type.BINARY)
    private byte[] objectGUID;

    @Attribute(name = LdapFields.CN)
    private String commonName;

    public Name getId() {
        return id;
    }

    public LdapGroupFull setId(Name id) {
        this.id = id;
        return this;
    }

    public byte[] getObjectGUID() {
        return objectGUID;
    }

    public LdapGroupFull setObjectGUID(byte[] objectGUID) {
        this.objectGUID = objectGUID;
        return this;
    }

    public String getCommonName() {
        return commonName;
    }

    public LdapGroupFull setCommonName(String commonName) {
        this.commonName = commonName;
        return this;
    }
}
