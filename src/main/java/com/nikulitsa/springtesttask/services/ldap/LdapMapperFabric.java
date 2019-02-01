package com.nikulitsa.springtesttask.services.ldap;

import com.nikulitsa.springtesttask.entities.ldap.AbstractLdapEntity;
import com.nikulitsa.springtesttask.entities.ldap.BuiltinDomain;
import com.nikulitsa.springtesttask.entities.ldap.LdapContainer;
import com.nikulitsa.springtesttask.entities.ldap.LdapFields;
import com.nikulitsa.springtesttask.entities.ldap.LdapGroup;
import com.nikulitsa.springtesttask.entities.ldap.LdapObjectClass;
import com.nikulitsa.springtesttask.entities.ldap.LdapPerson;
import com.nikulitsa.springtesttask.entities.ldap.OrganizationalUnit;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
public class LdapMapperFabric {

    public AttributesMapper<AbstractLdapEntity> ldapEntityMapper(LdapObjectClass ldapObjectClass) {
        return attributes -> {
            String dn = (String) attributes.get(LdapFields.DISTINGUISHED_NAME).get();
            byte[] objectGUID = (byte[]) attributes.get(LdapFields.OBJECT_GUID).get();

            switch (ldapObjectClass) {
                case GROUP:
                    return new LdapGroup(dn, objectGUID);
                case PERSON:
                    return new LdapPerson(dn, objectGUID);
                case CONTAINER:
                    return new LdapContainer(dn, objectGUID);
                case ORGANIZATIONAL_UNIT:
                    return new OrganizationalUnit(dn, objectGUID);
                case BUILTIN_DOMAIN:
                    return new BuiltinDomain(dn, objectGUID);
                default:
                    //should never happen
                    return null;
            }
        };
    }

    public AttributesMapper<String> dnMapper() {
        return attributes -> {
            Attribute distinguishedName = attributes.get(LdapFields.DISTINGUISHED_NAME);
            return (String) distinguishedName.get();
        };
    }

    public AttributesMapper<String> shitMapper() {
        return attributes -> {
            StringBuilder stringBuilder = new StringBuilder();
            NamingEnumeration<? extends Attribute> allAttributes = attributes.getAll();
            while (allAttributes.hasMore()) {
                Attribute current = allAttributes.next();
                stringBuilder.append("<li>").append(current).append("</li>").append("\n");
            }
            return stringBuilder.toString();
        };
    }

    public AttributesMapper<? extends GrantedAuthority> groupCnMapper() {
        return attributes -> {
            Attribute cn = attributes.get(LdapFields.CN);
            return new SimpleGrantedAuthority(
                ((String) cn.get())
                    .toUpperCase()
                    .replaceAll(" ", "_")
            );
        };
    }

    public AttributesMapper<List<String>> groupMembersMapper() {
        return attributes -> {
            Attribute members = attributes.get(LdapFields.MEMBER);
            if (members != null) {
                List<String> membersList = new LinkedList<>();
                NamingEnumeration<?> all = members.getAll();
                while (all.hasMore()) {
                    membersList.add((String) all.next());
                }
                return membersList;
            } else {
                return Collections.emptyList();
            }
        };
    }
}
