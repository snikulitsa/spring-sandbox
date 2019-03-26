package com.nikulitsa.springsandbox.config.activedirectory.properties;

import com.nikulitsa.springsandbox.entities.ldap.LdapFields;

/**
 * Часть настроек Active Directory с логином и паролем пользователя,
 * под которым приложение будет подключаться к LDAP.
 *
 * @author Sergey Nikulitsa
 */
public class User {

    /**
     * Distinguished Name.
     */
    private String dn;

    /**
     * Пароль.
     */
    private String password;

    /**
     * LDAP запрос для поиска учетных записей пользователей в Active Directory при авторизации по логину/паролю.
     * Воркфлоу такой: сперва по sAMAccountName запрашивается DN, вход производится по DN
     * чтобы не конфликтовать с DAO-логинами.
     */
    private String searchFilter = "(" + LdapFields.DISTINGUISHED_NAME + "={0})";

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

    public String getSearchFilter() {
        return searchFilter;
    }

    public User setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
        return this;
    }
}
