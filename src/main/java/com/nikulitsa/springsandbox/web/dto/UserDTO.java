package com.nikulitsa.springsandbox.web.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nikulitsa.springsandbox.config.userdetails.UserType;
import com.nikulitsa.springsandbox.entities.AppUser;

/**
 * DTO-объект для передачи {@link UserType#DAO} пользователя.
 *
 * @author Sergey Nikulitsa
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class UserDTO {

    private Long id;

    private String login;

    private String email;

    public UserDTO() {
    }

    public UserDTO(AppUser appUser) {
        this.id = appUser.getId();
        this.login = appUser.getUsername();
        this.email = appUser.getMail();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
