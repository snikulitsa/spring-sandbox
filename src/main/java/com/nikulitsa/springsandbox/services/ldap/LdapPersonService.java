package com.nikulitsa.springsandbox.services.ldap;

import com.nikulitsa.springsandbox.entities.ldap.objects.LdapPerson;
import com.nikulitsa.springsandbox.web.dto.ldap.LdapEntityByObjectGUIDRequest;

/**
 * Интерфес сервиса LDAP-пользователей.
 *
 * @author Sergey Nikulitsa
 */
public interface LdapPersonService extends BaseLdapEntityService<LdapPerson> {

    /**
     * Получение всех пользователей LDAP в виде строки.
     */
    String getAllUsers();

    /**
     * Получение DN пользователя по sAMAccountName.
     *
     * @param username имя пользователя
     * @return DN
     */
    String getUserDnByUsername(String username);

    /**
     * Получение {@link LdapPerson} по objectGUID.
     *
     * @param request {@link LdapEntityByObjectGUIDRequest}
     * @return {@link LdapPerson}
     */
    LdapPerson getByObjectGUID(LdapEntityByObjectGUIDRequest request);

    /**
     * Получение {@link LdapPerson} по DN.
     *
     * @param personDN DN
     * @return {@link LdapPerson}
     */
    LdapPerson getByDN(String personDN);
}
