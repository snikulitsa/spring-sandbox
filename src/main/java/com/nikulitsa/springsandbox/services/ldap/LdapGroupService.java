package com.nikulitsa.springsandbox.services.ldap;

import com.nikulitsa.springsandbox.entities.ldap.objects.LdapGroup;
import com.nikulitsa.springsandbox.web.dto.ldap.LdapEntityByObjectGUIDRequest;
import com.nikulitsa.springsandbox.web.dto.ldap.LdapTreeEntityResponse;

import java.util.Set;

/**
 * Интерфейс сервиса по работе с LDAP-группами.
 *
 * @author Sergey Nikulitsa
 */
public interface LdapGroupService extends BaseLdapEntityService<LdapGroup> {

    /**
     * Получение группы по objectGUID.
     *
     * @param request {@link LdapEntityByObjectGUIDRequest}
     * @return {@link LdapGroup}
     */
    LdapGroup getByObjectGUID(LdapEntityByObjectGUIDRequest request);

    /**
     * @see BaseLdapEntityService#getEntityByDN(String, Class).
     */
    LdapGroup getByDN(String groupDn);

    /**
     * Получение участников группы.
     *
     * @param groupDn Distinguished Name группы
     * @return {@link LdapTreeEntityResponse}
     */
    LdapTreeEntityResponse getGroupMembers(String groupDn);

    /**
     * Рекурсивное получение групп в которых состоит текущая группа.
     *
     * @param groupDN      DN текущей группы
     * @param loopDetector сет DN групп, который соберет все группы и исключит петли
     *                     (т.к группы могут состоять друг в друге).
     */
    void getGroupsMembershipRecursively(String groupDN, Set<String> loopDetector);
}
