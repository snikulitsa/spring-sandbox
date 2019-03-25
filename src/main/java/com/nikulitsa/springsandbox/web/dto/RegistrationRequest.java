package com.nikulitsa.springsandbox.web.dto;

/**
 * Запрос на регистрацию пользователя по email.
 *
 * @author Sergey Nikulitsa
 */
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
