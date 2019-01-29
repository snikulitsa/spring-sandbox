package com.nikulitsa.springtesttask.web.dto.ldap;

public class LdapDnByUsernameRequest {

    private String domain;

    private String username;

    public String getDomain() {
        return domain;
    }

    public LdapDnByUsernameRequest setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public LdapDnByUsernameRequest setUsername(String username) {
        this.username = username;
        return this;
    }
}
