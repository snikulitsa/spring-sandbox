package com.nikulitsa.springsandbox.services.ldap.impl;

import com.nikulitsa.springsandbox.entities.ldap.LdapObjectClass;
import com.nikulitsa.springsandbox.entities.ldap.objects.LdapGroup;
import com.nikulitsa.springsandbox.entities.ldap.tree.AbstractLdapTreeEntity;
import com.nikulitsa.springsandbox.services.ldap.LdapGroupService;
import com.nikulitsa.springsandbox.services.ldap.LdapMapperFactory;
import com.nikulitsa.springsandbox.services.ldap.LdapQueryFactory;
import com.nikulitsa.springsandbox.web.dto.ldap.LdapEntityByObjectGUIDRequest;
import com.nikulitsa.springsandbox.web.dto.ldap.LdapTreeEntityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.nikulitsa.springsandbox.utils.ExceptionFactory.entityNotFoundExceptionSupplier;
import static com.nikulitsa.springsandbox.utils.LdapExtendedUtils.searchSuccess;

/**
 * @author Sergey Nikulitsa
 * @see LdapGroupService
 */
@Service
public class LdapGroupServiceImpl extends AbstractLdapEntityService<LdapGroup> implements LdapGroupService {

    @Autowired
    public LdapGroupServiceImpl(LdapQueryFactory ldapQueryFactory,
                                LdapMapperFactory ldapMapperFactory,
                                LdapTemplate ldapTemplate) {
        super(ldapQueryFactory, ldapMapperFactory, ldapTemplate);
    }

    @Override
    public LdapGroup getByObjectGUID(LdapEntityByObjectGUIDRequest request) {
        return getEntityByObjectGUID(request, LdapGroup.class)
            .orElseThrow(entityNotFoundExceptionSupplier(LdapGroup.class, request.getObjectGUID()));
    }

    @Override
    public LdapGroup getByDN(String groupDn) {
        return getEntityByDN(groupDn, LdapGroup.class)
            .orElseThrow(entityNotFoundExceptionSupplier(LdapGroup.class, groupDn));
    }

    @Override
    public LdapTreeEntityResponse getGroupMembers(String groupDn) {

        List<String> groupMembersDn = getByDN(groupDn).getMembers();
        List<AbstractLdapTreeEntity> persons = new LinkedList<>();
        List<AbstractLdapTreeEntity> groups = new LinkedList<>();

        groupMembersDn.forEach(groupMemberDn -> {
            mapMembersDnToLdapTreeEntity(persons, groupMemberDn, LdapObjectClass.PERSON);
            mapMembersDnToLdapTreeEntity(groups, groupMemberDn, LdapObjectClass.GROUP);
        });

        return new LdapTreeEntityResponse()
            .setLdapPersons(persons)
            .setLdapGroups(groups);
    }

    /**
     * Преобразование Distinguished Name сущности в {@link AbstractLdapTreeEntity}.
     *
     * @param entities        коллекция, в которую записывается полученная {@link AbstractLdapTreeEntity}
     * @param groupMemberDn   Distinguished Name сущности
     * @param ldapObjectClass тип сущности {@link LdapObjectClass}
     */
    private void mapMembersDnToLdapTreeEntity(List<AbstractLdapTreeEntity> entities,
                                              String groupMemberDn,
                                              LdapObjectClass ldapObjectClass) {
        List<AbstractLdapTreeEntity> personSearch = ldapTemplate().search(
            ldapQueryFactory().ldapOneTreeEntityQuery(ldapObjectClass, groupMemberDn),
            ldapMapperFactory().ldapTreeEntityMapper(ldapObjectClass)
        );

        if (searchSuccess(personSearch)) {
            entities.add(personSearch.get(0));
        }
    }

    @Override
    public void getGroupsMembershipRecursively(String groupDN, Set<String> loopDetector) {
        if (loopDetector.add(groupDN)) {
            LdapGroup ldapGroup = getByDN(groupDN);
            List<String> memberOf = ldapGroup.getMemberOf();
            for (String parentDN : memberOf) {
                getGroupsMembershipRecursively(parentDN, loopDetector);
            }
        }
    }
}
