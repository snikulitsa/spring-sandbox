package com.nikulitsa.springtesttask.entities.ldap;

public interface LdapFields {
    //Attributes
    String OBJECT_CATEGORY = "objectCategory";
    String OBJECT_CLASS = "objectClass";
    String DISTINGUISHED_NAME = "distinguishedName";
    String USER_PRINCIPAL_NAME = "userPrincipalName";
    String MEMBER = "member";
    String CN = "cn";
    String OBJECT_GUID = "objectGUID";

    //Object classes
    String GROUP = "group";
    String CONTAINER = "container";
    String ORGANIZATIONAL_UNIT = "organizationalUnit";
    String PERSON = "person";
}
