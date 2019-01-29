package com.nikulitsa.springtesttask.config.ldap.properties;

public class Attributes {

    private String binary_attributes;

    private String person_object_category = "person";

    private String organizational_unit_object_class = "organizationalUnit";

    private String username = "sAMAccountName";

    public String getBinary_attributes() {
        return binary_attributes;
    }

    public Attributes setBinary_attributes(String binary_attributes) {
        this.binary_attributes = binary_attributes;
        return this;
    }

    public String getPerson_object_category() {
        return person_object_category;
    }

    public Attributes setPerson_object_category(String person_object_category) {
        this.person_object_category = person_object_category;
        return this;
    }

    public String getOrganizational_unit_object_class() {
        return organizational_unit_object_class;
    }

    public Attributes setOrganizational_unit_object_class(String organizational_unit_object_class) {
        this.organizational_unit_object_class = organizational_unit_object_class;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Attributes setUsername(String username) {
        this.username = username;
        return this;
    }
}
