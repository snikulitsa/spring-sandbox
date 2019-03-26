package com.nikulitsa.springsandbox.entities.ldap;

/**
 * Типы объектов, которые приложение может достать из LDAP.
 *
 * @author Sergey Nikulitsa
 */
public enum LdapObjectClass {

    GROUP(LdapFields.GROUP),
    CONTAINER(LdapFields.CONTAINER),
    ORGANIZATIONAL_UNIT(LdapFields.ORGANIZATIONAL_UNIT),
    BUILTIN_DOMAIN(LdapFields.BUILTIN_DOMAIN),
    PERSON(LdapFields.PERSON);

    private final String value;

    LdapObjectClass(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
