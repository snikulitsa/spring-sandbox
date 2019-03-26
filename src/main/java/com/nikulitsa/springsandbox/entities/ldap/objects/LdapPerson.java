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
 * Класс-сущность пользователь в LDAP.
 *
 * @author Sergey Nikulitsa
 */
@Entry(objectClasses = LdapFields.PERSON)
public final class LdapPerson {

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
     * Имя пользователя.
     */
    @Attribute(name = LdapFields.FIRST_NAME)
    private String firstName;

    /**
     * Фамилия пользователя.
     */
    @Attribute(name = LdapFields.LAST_NAME)
    private String lastName;

    /**
     * Отчество пользователя.
     */
    @Attribute(name = LdapFields.MIDDLE_NAME)
    private String middleName;

    /**
     * Описание.
     */
    @Attribute(name = LdapFields.DESCRIPTION)
    private String description;

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
     * Имя объекта в Active Directory.
     */
    @Attribute(name = LdapFields.NAME)
    private String name;

    /**
     * Логин в Active Directory.
     */
    @Attribute(name = LdapFields.S_AM_ACCOUNT_NAME)
    private String accountName;

    /**
     * Логин в Active Directory с указанием домена (m.smith@example.org).
     */
    @Attribute(name = LdapFields.USER_PRINCIPAL_NAME)
    private String userPrincipalName;

    /**
     * Distinguished Names групп, в которых состоит пользователь.
     */
    @Attribute(name = LdapFields.MEMBER_OF)
    private List<String> memberOf = new ArrayList<>();

    /**
     * Телефонный номер.
     */
    @Attribute(name = LdapFields.PHONE)
    private String phone;

    /**
     * Электронная почта.
     */
    @Attribute(name = LdapFields.MAIL)
    private String mail;

    public Name getId() {
        return id;
    }

    public LdapPerson setId(Name id) {
        this.id = id;
        return this;
    }

    public byte[] getObjectGUID() {
        return objectGUID;
    }

    public LdapPerson setObjectGUID(byte[] objectGUID) {
        this.objectGUID = objectGUID;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public LdapPerson setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public LdapPerson setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public LdapPerson setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public LdapPerson setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCn() {
        return cn;
    }

    public LdapPerson setCn(String cn) {
        this.cn = cn;
        return this;
    }

    public String getDn() {
        return dn;
    }

    public LdapPerson setDn(String dn) {
        this.dn = dn;
        return this;
    }

    public String getName() {
        return name;
    }

    public LdapPerson setName(String name) {
        this.name = name;
        return this;
    }

    public String getAccountName() {
        return accountName;
    }

    public LdapPerson setAccountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public String getUserPrincipalName() {
        return userPrincipalName;
    }

    public LdapPerson setUserPrincipalName(String userPrincipalName) {
        this.userPrincipalName = userPrincipalName;
        return this;
    }

    public List<String> getMemberOf() {
        return memberOf;
    }

    public LdapPerson setMemberOf(List<String> memberOf) {
        this.memberOf = memberOf;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public LdapPerson setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getMail() {
        return mail;
    }

    public LdapPerson setMail(String mail) {
        this.mail = mail;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LdapPerson that = (LdapPerson) o;
        return Arrays.equals(objectGUID, that.objectGUID);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(objectGUID);
    }
}
