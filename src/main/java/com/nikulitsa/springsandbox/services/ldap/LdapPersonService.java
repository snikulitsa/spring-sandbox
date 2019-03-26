package com.nikulitsa.springsandbox.services.ldap;

import com.nikulitsa.springsandbox.entities.ldap.objects.LdapPerson;
import com.nikulitsa.springsandbox.web.dto.ldap.LdapEntityByObjectGUIDRequest;

/**
 * Интерфес сервиса LDAP-пользователей.
 *
 * @author Sergey Nikulitsa
 */
public interface LdapPersonService extends BaseLdapEntityService<LdapPerson> {

    String getAllUsers();

    String getUserDnByUsername(String username);

    LdapPerson getByObjectGUID(LdapEntityByObjectGUIDRequest request);

    LdapPerson getByDN(String personDN);
}
