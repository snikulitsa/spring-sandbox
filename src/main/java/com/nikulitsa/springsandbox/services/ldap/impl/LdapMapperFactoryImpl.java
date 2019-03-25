package com.nikulitsa.springsandbox.services.ldap.impl;

import com.nikulitsa.springsandbox.entities.ldap.tree.AbstractLdapTreeEntity;
import com.nikulitsa.springsandbox.entities.ldap.tree.LdapTreeBuiltinDomain;
import com.nikulitsa.springsandbox.entities.ldap.tree.LdapTreeContainer;
import com.nikulitsa.springsandbox.entities.ldap.LdapFields;
import com.nikulitsa.springsandbox.entities.ldap.tree.LdapTreeGroup;
import com.nikulitsa.springsandbox.entities.ldap.LdapObjectClass;
import com.nikulitsa.springsandbox.entities.ldap.tree.LdapTreePerson;
import com.nikulitsa.springsandbox.entities.ldap.tree.LdapTreeOrganizationalUnit;
import com.nikulitsa.springsandbox.services.ldap.LdapMapperFactory;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Sergey Nikulitsa
 * @see LdapMapperFactory
 */
@Component
public class LdapMapperFactoryImpl implements LdapMapperFactory {

    @Override
    public AttributesMapper<AbstractLdapTreeEntity> ldapTreeEntityMapper(LdapObjectClass ldapObjectClass) {
        return attributes -> {
            String name = (String) attributes.get(LdapFields.NAME).get();
            String dn = (String) attributes.get(LdapFields.DISTINGUISHED_NAME).get();
            byte[] objectGUID = (byte[]) attributes.get(LdapFields.OBJECT_GUID).get();

            switch (ldapObjectClass) {
                case GROUP:
                    return new LdapTreeGroup(name, dn, objectGUID);
                case PERSON:
                    return new LdapTreePerson(name, dn, objectGUID);
                case CONTAINER:
                    return new LdapTreeContainer(name, dn, objectGUID);
                case ORGANIZATIONAL_UNIT:
                    return new LdapTreeOrganizationalUnit(name, dn, objectGUID);
                case BUILTIN_DOMAIN:
                    return new LdapTreeBuiltinDomain(name, dn, objectGUID);
                default:
                    //should never happen
                    return null;
            }
        };
    }

    @Override
    public AttributesMapper<String> dnMapper() {
        return attributes -> {
            Attribute distinguishedName = attributes.get(LdapFields.DISTINGUISHED_NAME);
            return (String) distinguishedName.get();
        };
    }

    @Override
    public AttributesMapper<String> debugMapper() {
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

    @Override
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

    @Override
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

    @Override
    public <T> ContextMapper<T> objectDirectoryContextMapper(LdapTemplate ldapTemplate, Class<T> clazz) {
        return ctx -> ldapTemplate
            .getObjectDirectoryMapper()
            .mapFromLdapDataEntry((DirContextOperations) ctx, clazz);
    }
}
