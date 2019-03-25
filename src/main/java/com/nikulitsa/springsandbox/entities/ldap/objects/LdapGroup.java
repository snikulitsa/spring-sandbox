package com.nikulitsa.springsandbox.entities.ldap.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nikulitsa.springsandbox.entities.ldap.LdapFields;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс-сущность группа в LDAP.
 *
 * @author Sergey Nikulitsa
 */
@Entry(objectClasses = LdapFields.GROUP)
public final class LdapGroup {

    /**
     * id сущности, который необходим для Spring ODM (изменяемый).
     */
    @Id
    @JsonIgnore
    private Name id;

    /**
     * Уникальный идентификатор сущности (неизменяемый).
     */
    @Attribute(name = LdapFields.OBJECT_GUID, type = Attribute.Type.BINARY)
    private byte[] objectGUID;

    /**
     * Common Name.
     */
    @Attribute(name = LdapFields.CN)
    private String cn;

    /**
     * Distinguished Name.
     */
    @Attribute(name = LdapFields.DISTINGUISHED_NAME)
    private String dn;

    /**
     * Описание.
     */
    @Attribute(name = LdapFields.DESCRIPTION)
    private String description;

    @Attribute(name = LdapFields.MEMBER)
    private List<String> members = new ArrayList<>();

    @Attribute(name = LdapFields.MEMBER_OF)
    private List<String> memberOf = new ArrayList<>();

    public Name getId() {
        return id;
    }

    public LdapGroup setId(Name id) {
        this.id = id;
        return this;
    }

    public byte[] getObjectGUID() {
        return objectGUID;
    }

    public LdapGroup setObjectGUID(byte[] objectGUID) {
        this.objectGUID = objectGUID;
        return this;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public List<String> getMemberOf() {
        return memberOf;
    }

    public void setMemberOf(List<String> memberOf) {
        this.memberOf = memberOf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LdapGroup ldapGroup = (LdapGroup) o;
        return Arrays.equals(objectGUID, ldapGroup.objectGUID);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(objectGUID);
    }
}
