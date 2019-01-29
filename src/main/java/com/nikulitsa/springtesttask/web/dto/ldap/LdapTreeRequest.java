package com.nikulitsa.springtesttask.web.dto.ldap;

public class LdapTreeRequest {

    private String dn;

    private String domain;

    public String getDn() {
        return dn;
    }

    public LdapTreeRequest setDn(String dn) {
        this.dn = dn;
        return this;
    }

    public String getDomain() {
        return domain;
    }

    public LdapTreeRequest setDomain(String domain) {
        this.domain = domain;
        return this;
    }
}
