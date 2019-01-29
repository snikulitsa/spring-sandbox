package com.nikulitsa.springtesttask.web.dto.ldap;

import java.util.ArrayList;
import java.util.List;

public class LdapTreeResponse {

    List<String> organizationUnits = new ArrayList<>();
    List<String> persons = new ArrayList<>();
    List<String> groups = new ArrayList<>();
    List<String> containers = new ArrayList<>();

    public List<String> getOrganizationUnits() {
        return organizationUnits;
    }

    public LdapTreeResponse setOrganizationUnits(List<String> organizationUnits) {
        this.organizationUnits = organizationUnits;
        return this;
    }

    public List<String> getPersons() {
        return persons;
    }

    public LdapTreeResponse setPersons(List<String> persons) {
        this.persons = persons;
        return this;
    }

    public List<String> getGroups() {
        return groups;
    }

    public LdapTreeResponse setGroups(List<String> groups) {
        this.groups = groups;
        return this;
    }

    public List<String> getContainers() {
        return containers;
    }

    public LdapTreeResponse setContainers(List<String> containers) {
        this.containers = containers;
        return this;
    }
}
