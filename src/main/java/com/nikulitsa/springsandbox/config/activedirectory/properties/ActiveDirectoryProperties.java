package com.nikulitsa.springsandbox.config.activedirectory.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.ldap.core.support.AbstractContextSource;
import org.springframework.stereotype.Component;

/**
 * Считыватель конфигурации Active Directory из properties файла.
 *
 * @author Sergey Nikulitsa
 */
@Component
@ConfigurationProperties(prefix = "ad")
public class ActiveDirectoryProperties {

    /**
     * Включение/отключение подсистемы авторизации через Active Directory.
     */
    private boolean enabled = false;

    /**
     * URL к контроллеру домена.
     */
    private String url = "ldap://localhost:389";

    /**
     * Корень, от которого будут производиться запросы (нужно указать домен).
     */
    private String base;

    /**
     * Таймаут ожидания ответа из LDAP в миллисекундах.
     */
    private String timeout = "5000";

    /**
     * @see AbstractContextSource#setReferral(String).
     */
    private String referralMode;

    /**
     * @see User
     */
    private final User user = new User();

    /**
     * @see Attributes
     */
    private final Attributes attributes = new Attributes();

    /**
     * @see Kerberos
     */
    private final Kerberos kerberos = new Kerberos();

    public boolean isEnabled() {
        return enabled;
    }

    public ActiveDirectoryProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public String getUrl() {
        return this.url;
    }

    public ActiveDirectoryProperties setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getBase() {
        return base;
    }

    public ActiveDirectoryProperties setBase(String base) {
        this.base = base;
        return this;
    }

    public String getTimeout() {
        return this.timeout;
    }

    public ActiveDirectoryProperties setTimeout(String timeout) {
        this.timeout = timeout;
        return this;
    }

    public User getUser() {
        return user;
    }

    public String getReferralMode() {
        return referralMode;
    }

    public ActiveDirectoryProperties setReferralMode(String referralMode) {
        this.referralMode = referralMode;
        return this;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public Kerberos getKerberos() {
        return kerberos;
    }
}
