package com.nikulitsa.springsandbox.web.dto.ldap;

import com.nikulitsa.springsandbox.entities.ldap.tree.AbstractLdapTreeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO-ветка LDAP дерева.
 *
 * @author Sergey Nikulitsa
 */
public class LdapTreeEntityResponse {

    private List<AbstractLdapTreeEntity> ldapPersons = new ArrayList<>();

    private List<AbstractLdapTreeEntity> ldapGroups = new ArrayList<>();

    private List<AbstractLdapTreeEntity> ldapContainers = new ArrayList<>();

    private List<AbstractLdapTreeEntity> builtinDomains = new ArrayList<>();

    private List<AbstractLdapTreeEntity> organizationalUnits = new ArrayList<>();

    public List<AbstractLdapTreeEntity> getLdapPersons() {
        return ldapPersons;
    }

    public LdapTreeEntityResponse setLdapPersons(List<AbstractLdapTreeEntity> ldapPersons) {
        this.ldapPersons = ldapPersons;
        return this;
    }

    public List<AbstractLdapTreeEntity> getLdapGroups() {
        return ldapGroups;
    }

    public LdapTreeEntityResponse setLdapGroups(List<AbstractLdapTreeEntity> ldapGroups) {
        this.ldapGroups = ldapGroups;
        return this;
    }

    public List<AbstractLdapTreeEntity> getLdapContainers() {
        return ldapContainers;
    }

    public LdapTreeEntityResponse setLdapContainers(List<AbstractLdapTreeEntity> ldapContainers) {
        this.ldapContainers = ldapContainers;
        return this;
    }

    public List<AbstractLdapTreeEntity> getBuiltinDomains() {
        return builtinDomains;
    }

    public LdapTreeEntityResponse setBuiltinDomains(List<AbstractLdapTreeEntity> builtinDomains) {
        this.builtinDomains = builtinDomains;
        return this;
    }

    public List<AbstractLdapTreeEntity> getOrganizationalUnits() {
        return organizationalUnits;
    }

    public LdapTreeEntityResponse setOrganizationalUnits(List<AbstractLdapTreeEntity> organizationalUnits) {
        this.organizationalUnits = organizationalUnits;
        return this;
    }
}
