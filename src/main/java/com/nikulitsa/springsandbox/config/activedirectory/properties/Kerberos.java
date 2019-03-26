package com.nikulitsa.springsandbox.config.activedirectory.properties;

/**
 * Часть настроек Active Directory с Kerberos авторизацией.
 *
 * @author Sergey Nikulitsa
 */
public class Kerberos {

    /**
     * Включение/отключение авторизации через Kerberos.
     */
    private boolean enabled = false;

    /**
     * LDAP запрос для поиска учетных записей пользователей при Kerberos авторизации.
     */
    private String searchFilter = "(| (userPrincipalName={0}) (sAMAccountName={0}))";

    /**
     * Путь к файлу с закрытыми ключами приложения.
     */
    private String keytabLocation = "/home/app.keytab";

    /**
     * Учетная запись приложения в Active Directory для Kerberos.
     */
    private String applicationAccount = "HTTP/app.localhost.localdomain@LOCALHOST.LOCALDOMAIN";

    /**
     * URL к Kerberos-серверу в домене.
     */
    private String krbHostUrl = "ldap://KRB-HOST.localhost.localdomain/";

    /**
     * База с которой производится поиск Kerberos-пользователей (пустая строка = корень).
     */
    private String searchBase = "";

    /**
     * Включение дебага Kerberos-механизмов приложения.
     */
    private boolean debugEnabled = false;

    /**
     * Является ли наше приложения инициатором Kerberos-проверок.
     * TODO уточнить...
     */
    private boolean isInitiator = false;

    public boolean isEnabled() {
        return enabled;
    }

    public Kerberos setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public String getSearchFilter() {
        return searchFilter;
    }

    public Kerberos setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
        return this;
    }

    public String getKeytabLocation() {
        return keytabLocation;
    }

    public Kerberos setKeytabLocation(String keytabLocation) {
        this.keytabLocation = keytabLocation;
        return this;
    }

    public String getApplicationAccount() {
        return applicationAccount;
    }

    public Kerberos setApplicationAccount(String applicationAccount) {
        this.applicationAccount = applicationAccount;
        return this;
    }

    public String getKrbHostUrl() {
        return krbHostUrl;
    }

    public Kerberos setKrbHostUrl(String krbHostUrl) {
        this.krbHostUrl = krbHostUrl;
        return this;
    }

    public String getSearchBase() {
        return searchBase;
    }

    public Kerberos setSearchBase(String searchBase) {
        this.searchBase = searchBase;
        return this;
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public Kerberos setDebugEnabled(boolean debugEnabled) {
        this.debugEnabled = debugEnabled;
        return this;
    }

    public boolean isInitiator() {
        return isInitiator;
    }

    public Kerberos setInitiator(boolean initiator) {
        this.isInitiator = initiator;
        return this;
    }
}
