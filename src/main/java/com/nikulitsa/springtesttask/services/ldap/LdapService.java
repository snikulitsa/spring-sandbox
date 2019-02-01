package com.nikulitsa.springtesttask.services.ldap;

import com.nikulitsa.springtesttask.config.activedirectory.ActiveDirectoryConfig;
import com.nikulitsa.springtesttask.config.activedirectory.properties.AbstractActiveDirectoryProperties;
import com.nikulitsa.springtesttask.entities.ldap.AbstractLdapEntity;
import com.nikulitsa.springtesttask.entities.ldap.LdapGroup;
import com.nikulitsa.springtesttask.entities.ldap.LdapObjectClass;
import com.nikulitsa.springtesttask.web.dto.ldap.LdapDnByUsernameRequest;
import com.nikulitsa.springtesttask.web.dto.ldap.LdapEntityByObjectGUIDRequest;
import com.nikulitsa.springtesttask.web.dto.ldap.LdapTreeEntityResponse;
import com.nikulitsa.springtesttask.web.dto.ldap.LdapTreeRequest;
import com.nikulitsa.springtesttask.web.dto.ldap.LdapTreeResponse;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.HardcodedFilter;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.naming.ldap.LdapName;
import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class LdapService {

    private final ActiveDirectoryConfig activeDirectoryConfig;
    private final LdapQueryFabric ldapQueryFabric;
    private final LdapMapperFabric ldapMapperFabric;

    public LdapService(ActiveDirectoryConfig activeDirectoryConfig,
                       LdapQueryFabric ldapQueryFabric,
                       LdapMapperFabric ldapMapperFabric) {
        this.activeDirectoryConfig = activeDirectoryConfig;
        this.ldapQueryFabric = ldapQueryFabric;
        this.ldapMapperFabric = ldapMapperFabric;
    }

    public String getAllUsers() {

        List<String> allUsers = new LinkedList<>();

        for (int ldapContextNumber : ActiveDirectoryConfig.LDAP_CONTEXT_NUMBERS) {
            if (activeDirectoryConfig.isLdapEnabled(ldapContextNumber)) {
                LdapTemplate ldapTemplate = activeDirectoryConfig.getLdapTemplateFromContext(ldapContextNumber);
                List<String> search = ldapTemplate.search(
                    "",
                    "(objectCategory=person)",
                    ldapMapperFabric.shitMapper()
                );
                String header =
                    "<li>###########################################"
                        + "####################################################</li>\n"
                        + "<li>#   " + activeDirectoryConfig.getBase(ldapContextNumber) + "  #</li>\n"
                        + "<li>############################################################"
                        + "###################################</li>\n";
                allUsers.add(header);
                allUsers.addAll(search);
            }
        }

        return String.join(
            "<li>====================================================================================</li>\n"
                + "<li>====================================================================================</li>\n",
            allUsers
        );
    }

    public List<String> getGroupMembers(LdapTreeRequest request) {

        String groupDn = request.getDn();
        String domain = request.getDomain();

        for (int ldapContextNumber : ActiveDirectoryConfig.LDAP_CONTEXT_NUMBERS) {
            if (domainMatch(domain, ldapContextNumber)) {
                LdapTemplate ldapTemplate = activeDirectoryConfig.getLdapTemplateFromContext(ldapContextNumber);
                List<List<String>> search = ldapTemplate.search(
                    ldapQueryFabric.objectByDn(groupDn),
                    ldapMapperFabric.groupMembersMapper()
                );

                if (LdapSearchUtils.searchSuccess(search)) {
                    return search.get(0);
                }
            }
        }

        return Collections.emptyList();
    }


    public String getDnByUsername(LdapDnByUsernameRequest request) {

        String domain = request.getDomain();
        String username = request.getUsername();

        for (int ldapContextNumber : ActiveDirectoryConfig.LDAP_CONTEXT_NUMBERS) {
            if (domainMatch(domain, ldapContextNumber)) {
                LdapTemplate ldapTemplate = activeDirectoryConfig.getLdapTemplateFromContext(ldapContextNumber);
                AbstractActiveDirectoryProperties ldapProperties = activeDirectoryConfig.getActiveDirectoryPropertiesFromContext(ldapContextNumber);
                List<String> search = ldapTemplate.search(
                    ldapQueryFabric.dnByUsername(username, ldapProperties),
                    ldapMapperFabric.dnMapper()
                );

                if (LdapSearchUtils.searchSuccess(search)) {
                    return search.get(0);
                }
            }
        }

        throw new UsernameNotFoundException("User: " + username + " does not exist");
    }

    public List<String> getAllDomains() {
        List<String> domains = new LinkedList<>();

        for (int ldapContextNumber : ActiveDirectoryConfig.LDAP_CONTEXT_NUMBERS) {
            AbstractActiveDirectoryProperties ldapProperties = activeDirectoryConfig.getActiveDirectoryPropertiesFromContext(ldapContextNumber);
            if (ldapProperties.isEnabled()) {
                domains.add(ldapProperties.getBase());
            }
        }

        return domains;
    }

    public LdapTreeEntityResponse getLdapTreeEntityResponse(LdapTreeRequest request) {
        String domain = request.getDomain();
        String dn = request.getDn();
        return getLdapTreeEntityResponse(domain, dn);
    }

    private LdapTreeEntityResponse getLdapTreeEntityResponse(String domain, String dn) {
        for (int adContext : ActiveDirectoryConfig.LDAP_CONTEXT_NUMBERS) {
            if (domainMatch(domain, adContext)) {
                dn = prepareDn(dn, adContext);
                LdapTemplate ldapTemplate = activeDirectoryConfig.getLdapTemplateFromContext(adContext);
                return getLdapTreeEntityResponse(dn, ldapTemplate);
            }
        }

        throw new EntityNotFoundException("Domain '" + domain + "' not present in system.");
    }

    private LdapTreeEntityResponse getLdapTreeEntityResponse(String dn, LdapTemplate ldapTemplate) {
        return new LdapTreeEntityResponse()
            .setLdapGroups(
                search(LdapObjectClass.GROUP, ldapTemplate, dn)
            )
            .setLdapPersons(
                search(LdapObjectClass.PERSON, ldapTemplate, dn)
            )
            .setLdapContainers(
                search(LdapObjectClass.CONTAINER, ldapTemplate, dn)
            )
            .setBuiltinDomains(
                search(LdapObjectClass.BUILTIN_DOMAIN, ldapTemplate, dn)
            )
            .setOrganizationalUnits(
                search(LdapObjectClass.ORGANIZATIONAL_UNIT, ldapTemplate, dn)
            );
    }

    private List<AbstractLdapEntity> search(LdapObjectClass ldapObjectClass, LdapTemplate ldapTemplate, String dn) {
        return ldapTemplate.search(
            ldapQueryFabric.ldapEntityQuery(dn, ldapObjectClass),
            ldapMapperFabric.ldapEntityMapper(ldapObjectClass)
        );
    }


    public LdapTreeResponse getLdapObject(LdapTreeRequest request) {
        String domain = request.getDomain();
        String dn = request.getDn();
        return getLdapObject(domain, dn);
    }

    private LdapTreeResponse getLdapObject(String domain, String dn) {

        for (int ldapContextNumber : ActiveDirectoryConfig.LDAP_CONTEXT_NUMBERS) {
            if (domainMatch(domain, ldapContextNumber)) {
                dn = prepareDn(dn, ldapContextNumber);

                LdapTemplate ldapTemplate = activeDirectoryConfig
                    .getLdapTemplateFromContext(ldapContextNumber);

                return getLdapObject(dn, ldapTemplate);
            }
        }

        throw new EntityNotFoundException("Domain '" + domain + "' not present in system.");
    }

    private LdapTreeResponse getLdapObject(String dn,
                                           LdapTemplate ldapTemplate) {

        List<String> persons = ldapTemplate.search(
            ldapQueryFabric.personsQuery(dn),
            ldapMapperFabric.dnMapper()
        );

        List<String> ou = ldapTemplate.search(
            ldapQueryFabric.organizationalUnitsQuery(dn),
            ldapMapperFabric.dnMapper()
        );

        List<String> groups = ldapTemplate.search(
            ldapQueryFabric.groupsQuery(dn),
            ldapMapperFabric.dnMapper()
        );

        List<String> containers = ldapTemplate.search(
            ldapQueryFabric.containersQuery(dn),
            ldapMapperFabric.dnMapper()
        );

        return new LdapTreeResponse()
            .setPersons(persons)
            .setOrganizationUnits(ou)
            .setGroups(groups)
            .setContainers(containers);
    }

    private boolean domainMatch(String domain, int contextNumber) {
        return domain.equalsIgnoreCase(activeDirectoryConfig.getBase(contextNumber));
    }

    private String prepareDn(String dn, int contextNumber) {
        if (dn != null) {
            return cutBase(dn, contextNumber);
        } else {
            return "";
        }
    }

    private String cutBase(String dn, int contextNumber) {
        LdapName ldapName = LdapUtils.newLdapName(dn);
        LdapName base = LdapUtils.newLdapName(activeDirectoryConfig.getBase(contextNumber));
        LdapName result = LdapUtils.removeFirst(ldapName, base);
        return result.toString();
    }

    public String getLdapEntityByObjectGUID(LdapEntityByObjectGUIDRequest request) {
        LdapTemplate ldapTemplate = activeDirectoryConfig.getLdapTemplateFromContext(1);
        byte[] objectGUID = request.getObjectGUID();
        return ldapTemplate.search(
            "",
            ldapQueryFabric.ldapEntityByObjectGUIDQuery(objectGUID),
            ldapMapperFabric.dnMapper()
        ).get(0);
    }
}
