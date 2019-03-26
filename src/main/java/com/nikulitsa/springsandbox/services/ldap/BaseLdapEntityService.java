package com.nikulitsa.springsandbox.services.ldap;

import com.nikulitsa.springsandbox.web.dto.ldap.LdapEntityByObjectGUIDRequest;
import org.springframework.ldap.core.LdapTemplate;

import java.util.Optional;

/**
 * Интерфейс базового сервиса по работе с LDAP-сущностями.
 *
 * @param <T> LDAP-сущность
 * @author Sergey Nikulitsa
 */
public interface BaseLdapEntityService<T> {

    /**
     * Получение LDAP-сущности по objectGUID.
     *
     * @param request {@link LdapEntityByObjectGUIDRequest}
     * @param clazz   класс сущности
     * @return LDAP-сущность
     */
    Optional<T> getEntityByObjectGUID(LdapEntityByObjectGUIDRequest request, Class<T> clazz);

    /**
     * Получение LDAP-сущности по Distinguished Name.
     *
     * @param dn    Distinguished Name
     * @param clazz класс сущности
     * @return LDAP-сущность
     */
    Optional<T> getEntityByDN(String dn, Class<T> clazz);

    LdapTemplate ldapTemplate();

    LdapQueryFactory ldapQueryFactory();

    LdapMapperFactory ldapMapperFactory();
}
