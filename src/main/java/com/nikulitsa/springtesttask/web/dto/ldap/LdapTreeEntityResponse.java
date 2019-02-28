package com.nikulitsa.springtesttask.web.dto.ldap;

import com.nikulitsa.springtesttask.entities.ldap.AbstractLdapEntity;

import java.util.ArrayList;
import java.util.List;

public class LdapTreeEntityResponse {

    private List<AbstractLdapEntity> ldapPersons = new ArrayList<>();

    private List<AbstractLdapEntity> ldapGroups = new ArrayList<>();

    private List<AbstractLdapEntity> ldapContainers = new ArrayList<>();

    private List<AbstractLdapEntity> builtinDomains = new ArrayList<>();

    private List<AbstractLdapEntity> organizationalUnits = new ArrayList<>();

    public List<AbstractLdapEntity> getLdapPersons() {
        return ldapPersons;
    }

    public LdapTreeEntityResponse setLdapPersons(List<AbstractLdapEntity> ldapPersons) {
        this.ldapPersons = ldapPersons;
        return this;
    }

    public List<AbstractLdapEntity> getLdapGroups() {
        return ldapGroups;
    }

    public LdapTreeEntityResponse setLdapGroups(List<AbstractLdapEntity> ldapGroups) {
        this.ldapGroups = ldapGroups;
        return this;
    }

    public List<AbstractLdapEntity> getLdapContainers() {
        return ldapContainers;
    }

    public LdapTreeEntityResponse setLdapContainers(List<AbstractLdapEntity> ldapContainers) {
        this.ldapContainers = ldapContainers;
        return this;
    }

    public List<AbstractLdapEntity> getBuiltinDomains() {
        return builtinDomains;
    }

    public LdapTreeEntityResponse setBuiltinDomains(List<AbstractLdapEntity> builtinDomains) {
        this.builtinDomains = builtinDomains;
        return this;
    }

    public List<AbstractLdapEntity> getOrganizationalUnits() {
        return organizationalUnits;
    }

    public LdapTreeEntityResponse setOrganizationalUnits(List<AbstractLdapEntity> organizationalUnits) {
        this.organizationalUnits = organizationalUnits;
        return this;
    }
}
