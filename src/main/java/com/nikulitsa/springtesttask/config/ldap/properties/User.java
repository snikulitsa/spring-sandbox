package com.nikulitsa.springtesttask.config.ldap.properties;

public class User {

    private String dn;

    private String password;

    private String search_filter = "(distinguishedName={0})";

    public String getDn() {
        return dn;
    }

    public User setDn(String dn) {
        this.dn = dn;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSearch_filter() {
        return search_filter;
    }

    public User setSearch_filter(String search_filter) {
        this.search_filter = search_filter;
        return this;
    }
}
