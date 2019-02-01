package com.nikulitsa.springtesttask.config.activedirectory.properties;

public class Attributes {

    private String binary_attributes;

    private String username = "sAMAccountName";

    public String getBinary_attributes() {
        return binary_attributes;
    }

    public Attributes setBinary_attributes(String binary_attributes) {
        this.binary_attributes = binary_attributes;
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
