package com.nikulitsa.springtesttask.config.activedirectory.properties;

public abstract class AbstractActiveDirectoryProperties {

    private boolean enabled = false;

    private String url = "ldap://localhost:389";

    private String base;

    private String timeout = "5000";

    private final User user = new User();

    private String referral_mode;

    private final Attributes attributes = new Attributes();

    private final Kerberos kerberos = new Kerberos();

    public boolean isEnabled() {
        return enabled;
    }

    public AbstractActiveDirectoryProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public String getUrl() {
        return this.url;
    }

    public AbstractActiveDirectoryProperties setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getBase() {
        return base;
    }

    public AbstractActiveDirectoryProperties setBase(String base) {
        this.base = base;
        return this;
    }

    public String getTimeout() {
        return this.timeout;
    }

    public AbstractActiveDirectoryProperties setTimeout(String timeout) {
        this.timeout = timeout;
        return this;
    }

    public User getUser() {
        return user;
    }

    public String getReferral_mode() {
        return referral_mode;
    }

    public AbstractActiveDirectoryProperties setReferral_mode(String referral_mode) {
        this.referral_mode = referral_mode;
        return this;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public Kerberos getKerberos() {
        return kerberos;
    }
}
