package com.nikulitsa.springsandbox.web.dto.ldap;

/**
 * Запрос на получение LDAP-сущности по objectGUID.
 *
 * @author Sergey Nikulitsa
 */
public class LdapEntityByObjectGUIDRequest {

    /**
     * Уникальный идентификатор объекта в LDAP.
     */
    private byte[] objectGUID;

    public byte[] getObjectGUID() {
        return objectGUID;
    }

    public LdapEntityByObjectGUIDRequest setObjectGUID(byte[] objectGUID) {
        this.objectGUID = objectGUID;
        return this;
    }
}
