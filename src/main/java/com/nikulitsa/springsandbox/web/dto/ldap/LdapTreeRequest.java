package com.nikulitsa.springsandbox.web.dto.ldap;

/**
 * Запрос на получение ветки LDAP-дерева.
 *
 * @author Sergey Nikulitsa
 */
public class LdapTreeRequest {

    private String dn;

    public String getDn() {
        return dn;
    }

    public LdapTreeRequest setDn(String dn) {
        this.dn = dn;
        return this;
    }
}
