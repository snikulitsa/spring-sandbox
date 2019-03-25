package com.nikulitsa.springsandbox.services.ldap;

import com.nikulitsa.springsandbox.web.dto.ldap.LdapEntityByObjectGUIDRequest;
import com.nikulitsa.springsandbox.web.dto.ldap.LdapTreeEntityResponse;

/**
 * @author Sergey Nikulitsa
 */
public interface LdapTreeService {

    String getAllUsers();

    LdapTreeEntityResponse getLdapTreeEntityResponse(String baseDn);

    String getLdapEntityByObjectGUID(LdapEntityByObjectGUIDRequest request);

    String getUserDnByUsername(String username);
}
