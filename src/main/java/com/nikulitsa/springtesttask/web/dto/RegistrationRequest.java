package com.nikulitsa.springtesttask.web.dto;

public class RegistrationRequest {

    private String email;

    public String getEmail() {
        return email;
    }

    public RegistrationRequest setEmail(String email) {
        this.email = email;
        return this;
    }
}
