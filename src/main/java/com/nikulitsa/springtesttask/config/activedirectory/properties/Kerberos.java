package com.nikulitsa.springtesttask.config.activedirectory.properties;

public class Kerberos {

    private boolean enabled = false;

    private String search_filter = "(| (userPrincipalName={0}) (sAMAccountName={0}))";

    private String keytab_location = "/home/app.keytab";

    private String application_account = "HTTP/app.localhost.localdomain@LOCALHOST.LOCALDOMAIN";

    private String krb_host_url = "ldap://KRB-HOST.localhost.localdomain/";

    private String search_base = "";

    private boolean debug_enabled = false;

    private boolean is_initiator = false;

    public boolean isEnabled() {
        return enabled;
    }

    public Kerberos setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public String getSearch_filter() {
        return search_filter;
    }

    public Kerberos setSearch_filter(String search_filter) {
        this.search_filter = search_filter;
        return this;
    }

    public String getKeytab_location() {
        return keytab_location;
    }

    public Kerberos setKeytab_location(String keytab_location) {
        this.keytab_location = keytab_location;
        return this;
    }

    public String getApplication_account() {
        return application_account;
    }

    public Kerberos setApplication_account(String application_account) {
        this.application_account = application_account;
        return this;
    }

    public String getKrb_host_url() {
        return krb_host_url;
    }

    public Kerberos setKrb_host_url(String krb_host_url) {
        this.krb_host_url = krb_host_url;
        return this;
    }

    public String getSearch_base() {
        return search_base;
    }

    public Kerberos setSearch_base(String search_base) {
        this.search_base = search_base;
        return this;
    }

    public boolean isDebug_enabled() {
        return debug_enabled;
    }

    public Kerberos setDebug_enabled(boolean debug_enabled) {
        this.debug_enabled = debug_enabled;
        return this;
    }

    public boolean isInitiator() {
        return is_initiator;
    }

    public Kerberos setIs_initiator(boolean is_initiator) {
        this.is_initiator = is_initiator;
        return this;
    }
}
