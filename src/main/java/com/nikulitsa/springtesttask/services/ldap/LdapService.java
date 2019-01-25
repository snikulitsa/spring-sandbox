package com.nikulitsa.springtesttask.services.ldap;

import com.nikulitsa.springtesttask.config.LdapTemplateConfig;
import com.nikulitsa.springtesttask.web.dto.ldap.LdapTreeResponse;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.ContainerCriteria;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.naming.ldap.LdapName;
import java.util.List;

import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Service
public class LdapService {

    private final LdapTemplate ldapTemplate;
    private final LdapTemplateConfig ldapTemplateConfig;
    private final LdapQueryFabric ldapQueryFabric;
    private final LdapMapperFabric ldapMapperFabric;

    public LdapService(LdapTemplate ldapTemplate,
                       LdapTemplateConfig ldapTemplateConfig,
                       LdapQueryFabric ldapQueryFabric,
                       LdapMapperFabric ldapMapperFabric) {
        this.ldapTemplate = ldapTemplate;
        this.ldapTemplateConfig = ldapTemplateConfig;
        this.ldapQueryFabric = ldapQueryFabric;
        this.ldapMapperFabric = ldapMapperFabric;
    }

    public String getAllUsers() {

        List<String> users = ldapTemplate.search(
            "",
            "(objectCategory=person)",
            ldapMapperFabric.shitMapper()
        );

        return String.join(
            "<li>====================================================================================</li>\n"
                + "<li>====================================================================================</li>\n",
            users
        );
    }

    public LdapTreeResponse getByDnBase(String baseDn) {
        baseDn = prepareBase(baseDn);

        List<String> persons = ldapTemplate.search(
            ldapQueryFabric.personsQuery(baseDn),
            ldapMapperFabric.dnMapper()
        );

        List<String> ou = ldapTemplate.search(
            ldapQueryFabric.organizationalUnitsQuery(baseDn),
            ldapMapperFabric.dnMapper()
        );

        List<String> groups = ldapTemplate.search(
            ldapQueryFabric.groupsQuery(baseDn),
            ldapMapperFabric.dnMapper()
        );

        List<String> containers = ldapTemplate.search(
            ldapQueryFabric.containersQuery(baseDn),
            ldapMapperFabric.dnMapper()
        );

        return new LdapTreeResponse()
            .setPersons(persons)
            .setOrganizationUnits(ou)
            .setGroups(groups)
            .setContainers(containers);
    }

    public List<String> getGroupMembers(String groupDn) {
        List<List<String>> search = ldapTemplate.search(
            ldapQueryFabric.objectByDn(groupDn),
            ldapMapperFabric.groupMembersMapper()
        );
        return search.get(0);
    }


    public String getDnByUsername(String username) {
        ContainerCriteria sAMAccountName = query().where("sAMAccountName").is(username);
        List<String> search = ldapTemplate.search(sAMAccountName, ldapMapperFabric.dnMapper());
        if (search != null && !search.isEmpty()) {
            return search.get(0);
        } else {
            throw new UsernameNotFoundException("User: " + username + " does not exist");
        }
    }

    public String prepareBase(String dn) {
        if (dn != null) {
            return cutBase(dn);
        } else {
            return "";
        }
    }

    public String cutBase(String dn) {
        LdapName ldapName = LdapUtils.newLdapName(dn);
        LdapName base = LdapUtils.newLdapName(ldapTemplateConfig.getBase());
        LdapName result = LdapUtils.removeFirst(ldapName, base);
        return result.toString();
    }


}
