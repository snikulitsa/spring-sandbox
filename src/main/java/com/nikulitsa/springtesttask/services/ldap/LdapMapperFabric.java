package com.nikulitsa.springtesttask.services.ldap;

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

    public AttributesMapper<String> dnMapper() {
        return attributes -> {
            Attribute distinguishedName = attributes.get("distinguishedName");
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
            Attribute cn = attributes.get("cn");
            return new SimpleGrantedAuthority(
                ((String) cn.get()).toUpperCase()
            );
        };
    }

    public AttributesMapper<List<String>> groupMembersMapper() {
        return attributes -> {
            Attribute members = attributes.get("member");
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
