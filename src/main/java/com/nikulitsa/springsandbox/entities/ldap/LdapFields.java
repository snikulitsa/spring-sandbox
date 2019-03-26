package com.nikulitsa.springsandbox.entities.ldap;

/**
 * Интерфейс с константами полей в LDAP.
 *
 * @author Sergey Nikulitsa
 */
public interface LdapFields {

    //Object classes
    String GROUP = "group";
    String CONTAINER = "container";
    String ORGANIZATIONAL_UNIT = "organizationalUnit";
    String BUILTIN_DOMAIN = "builtinDomain";
    String PERSON = "person";

    //Attributes
    String OBJECT_CLASS = "objectClass";
    String DISTINGUISHED_NAME = "distinguishedName";
    String USER_PRINCIPAL_NAME = "userPrincipalName";
    String MEMBER = "member";
    String MEMBER_OF = "memberOf";
    String CN = "cn";
    String OBJECT_GUID = "objectGUID";
    String S_AM_ACCOUNT_NAME = "sAMAccountName";
    String NAME = "name";
    String DESCRIPTION = "description";
    String FIRST_NAME = "givenName";
    String LAST_NAME = "sn";
    String MIDDLE_NAME = "initials";
    String PHONE = "telephoneNumber";
    String MAIL = "mail";
}
