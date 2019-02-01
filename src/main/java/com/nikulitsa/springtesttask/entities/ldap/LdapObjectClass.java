package com.nikulitsa.springtesttask.entities.ldap;

public enum LdapObjectClass {

    GROUP("group"),
    CONTAINER("container"),
    ORGANIZATIONAL_UNIT("organizationalUnit"),
    BUILTIN_DOMAIN("builtinDomain"),
    PERSON("person");

    private final String value;

    private LdapObjectClass(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
